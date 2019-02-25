package com.example.prudentialfinalproject;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.MutableLiveData;
import android.content.Intent;
import android.databinding.Bindable;
import android.databinding.BindingAdapter;
import android.databinding.Observable;
import android.databinding.PropertyChangeRegistry;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.prudentialfinalproject.models.Constants;
import com.example.prudentialfinalproject.utillities.Utilities;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

public class UserViewModel extends AndroidViewModel implements Observable{
    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;
    MutableLiveData<String> userName = new MutableLiveData<>();
    private String retrievedUserName;
    private String retrievedPassword;
    FirebaseDatabase database;
    PropertyChangeRegistry propertyChangeRegistry = new PropertyChangeRegistry();
    @Bindable
    private String emailValidStatus;
    @Bindable
    private String passwordValidStatus;

    public UserViewModel(@NonNull Application application) {
        super(application);

        firebaseAuth = FirebaseAuth.getInstance();
    }

//    @BindingAdapter("imageUrl")
//    public static void loadImage(ImageView view, ) {
//        Glide.with(view.getContext())
//                .load(imageUrl)
//                .apply(new RequestOptions().override(400, 400))
//                .into(view);
//    }


    public void handleClick(final View view){
        Log.d("TAG", "In handleClick");
        userName.setValue(retrievedUserName);
        firebaseAuth.signInWithEmailAndPassword(retrievedUserName, retrievedPassword)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d("TAG", "signInWithEmail:success");
                            firebaseUser = firebaseAuth.getCurrentUser();
                            Intent intent = new Intent(view.getContext(), Main2Activity.class);
                            view.getContext().startActivity(intent);

                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w("TAG", "signInWithEmail:failure", task.getException());


                        }

                    }
                });

//        firebaseAuth.createUserWithEmailAndPassword(retrievedUserName, retrievedPassword)
//                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
//                    @Override
//                    public void onComplete(@NonNull Task<AuthResult> task) {
//                        if (task.isSuccessful()) {
//                            // Sign in success, update UI with the signed-in user's information
//                            Log.d("TAG", "createUserWithEmail:success");
//                            FirebaseUser user = firebaseAuth.getCurrentUser();
//
//                        } else {
//                            // If sign in fails, display a message to the user.
//                            Log.w("TAG", "createUserWithEmail:failure", task.getException());
//
//                        }
//                    }
//                });
    }

    public void afterPasswordTextChanged(CharSequence charSequence){

        if(Utilities.isPasswordValid(charSequence.toString())){
            setPasswordValidStatus(Constants.VALID_PASSWORD);
            retrievedPassword = charSequence.toString();
            Log.d("TAG", "Password: " + retrievedPassword);
        }
        else if(!charSequence.toString().isEmpty()){
            setPasswordValidStatus(Constants.INVALID_PASSWORD);

        }
    }

    public void afterEmailTextChanged(CharSequence charSequence){
        if(Utilities.isUserEmailValid(charSequence.toString())){
            setEmailValidStatus(Constants.VALID_EMAIL);
            retrievedUserName = charSequence.toString();
            Log.d("TAG", "Password: " + retrievedUserName);
        }
        else if(!charSequence.toString().isEmpty()){
            setEmailValidStatus(Constants.INVALID_EMAIL);

        }
    }

    public String getEmailValidStatus() {
        return emailValidStatus;
    }

    public void setEmailValidStatus(String emailValidStatus) {
        this.emailValidStatus = emailValidStatus;
        notifySingleBindedItems(BR.emailValidStatus);
    }

    public String getPasswordValidStatus() {
        return passwordValidStatus;

    }

    public void setPasswordValidStatus(String passwordValidStatus) {
        this.passwordValidStatus = passwordValidStatus;
        notifySingleBindedItems(BR.passwordValidStatus);

    }

    @Override
    public void addOnPropertyChangedCallback(Observable.OnPropertyChangedCallback callback) {
        propertyChangeRegistry.add(callback);
    }

    @Override
    public void removeOnPropertyChangedCallback(Observable.OnPropertyChangedCallback callback) {
        propertyChangeRegistry.remove(callback);
    }

    public void notifySingleBindedItems(int fieldId){
        propertyChangeRegistry.notifyCallbacks(this, fieldId, null);

    }



}
