package com.example.loginsignup2;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AddRestaurantFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AddRestaurantFragment extends Fragment {

    private EditText etName, etAddress, etCuisineType, etRating;
    private Button btnAdd;
    private FirebaseServices fbs;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public AddRestaurantFragment() {
        // Required empty public constructor
    }

    @Override
    public void onStart() {
        super.onStart();
        etName = getView().findViewById(R.id.etNameAddRestaurant);
        etAddress = getView().findViewById(R.id.etAddressAddRestaurant);
        etCuisineType = getView().findViewById(R.id.etCuisineTypeAddFragment);
        etRating = getView().findViewById(R.id.etRatingAddFragment);
        btnAdd = getView().findViewById(R.id.btnAddAddFragment);
        fbs = FirebaseServices.getInstance();
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // get data from screen
                String name = etName.getText().toString();
                String address = etAddress.getText().toString();
                String cuisineType = etCuisineType.getText().toString();
                String rating = etRating.getText().toString();
                // check data
                if (name.trim().isEmpty() || address.trim().isEmpty() || cuisineType.trim().isEmpty() || rating.trim().isEmpty()) {
                    Toast.makeText(getActivity(), "Some data missing!", Toast.LENGTH_SHORT).show();
                    return;
                }
                // add to firebase
                int ratingInt = Integer.parseInt(rating);
                Restaurant rest = new Restaurant(name, address, cuisineType, ratingInt, "");
                fbs.getDb().collection("restaurants").add(rest).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Toast.makeText(getActivity(), "Success!", Toast.LENGTH_SHORT).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getActivity(), "Failure!", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AddRestaurantFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AddRestaurantFragment newInstance(String param1, String param2) {
        AddRestaurantFragment fragment = new AddRestaurantFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add_restaurant, container, false);
    }
}