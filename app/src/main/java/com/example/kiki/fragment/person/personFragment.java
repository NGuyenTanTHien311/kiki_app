package com.example.kiki.fragment.person;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.kiki.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;


public class personFragment extends Fragment {
    FirebaseAuth auth = FirebaseAuth.getInstance();
    Button submitChanges;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_person, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        EditText username = view.findViewById(R.id.userName);
        EditText useremail = view.findViewById(R.id.userEmail);

        TextView displayedUsername = view.findViewById(R.id.usernickname);
        TextView displayedUserEmail = view.findViewById(R.id.useremail);
        submitChanges = view.findViewById(R.id.submitChanges);

        // Hiển thị thông tin user  bằng Firebase Authentication
        displayedUserEmail.setText(auth.getCurrentUser().getEmail());
        displayedUsername.setText(auth.getCurrentUser().getDisplayName());
        useremail.setText(auth.getCurrentUser().getEmail());

        // Lưu thay đổi
        submitChanges.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Set lại displayname trên giao diện
                displayedUsername.setText(username.getText().toString());
                // Thay đổi trong firebase authentication
                UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                        .setDisplayName(username.getText().toString()).build();
                username.setText("");
                FirebaseUser firebaseUser = auth.getCurrentUser();
                firebaseUser.updateProfile(profileUpdates);
            }
        });
    }
}