package com.example.prudentialfinalproject;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.content.Context;
import android.content.Intent;
import android.databinding.Bindable;
import android.databinding.Observable;
import android.databinding.PropertyChangeRegistry;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;

import com.example.prudentialfinalproject.activities.AccountsActivity;
import com.example.prudentialfinalproject.activities.SignUpActivity;
import com.example.prudentialfinalproject.models.Constants;
import com.example.prudentialfinalproject.models.Loan;
import com.example.prudentialfinalproject.models.Transaction;
import com.example.prudentialfinalproject.models.User;
import com.example.prudentialfinalproject.utillities.Utilities;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import static com.example.prudentialfinalproject.Constants.FILE_NAME;

public class UserLoginViewModel extends ViewModel implements Observable{
    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;
    private String retrievedUserName;
    private String retrievedPassword;
    private String accountType;
    private String checkingAccountNumber;
    private String loanAccountNumber;
    private String selectedItemFromSpinner;
    boolean saveId = false;
    MutableLiveData<String> accountNumLiveData;
    MutableLiveData<String> retrievedUserNameLiveData;
    User globalUser;
    private String savingsAccountNumber;
    PropertyChangeRegistry propertyChangeRegistry = new PropertyChangeRegistry();
    List<Loan> loanList;
    @Bindable
    private String emailValidStatus;
    @Bindable
    private String passwordValidStatus;
    @Bindable
    private String accountNumberValidStatus;
    @Bindable
    private String savedUserName;
    FirebaseDatabase database;
    DatabaseReference myRef;
    Context context;

//    public UserLoginViewModel() {
//
//    }

    public void init(@NonNull Application application){
        context = application;
        database = FirebaseDatabase.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();
        myRef = database.getReference();
        loanList = new ArrayList<>();
        globalUser = new User();
        retrievedUserNameLiveData = new MutableLiveData<>();
        checkForSavedUserName();
    }

    private void checkForSavedUserName(){
        List<String> fileList = Arrays.asList(context.getFilesDir().list());
        if(fileList.contains(FILE_NAME)){
            int fileLocation = fileList.indexOf(FILE_NAME);
            File file = context.getFilesDir().listFiles()[fileLocation];
            Log.d("TAG", "File Exists: " + file.getName());
            FileInputStream fileInputStream;
            try {
                fileInputStream = context.openFileInput(FILE_NAME);
                BufferedReader reader = new BufferedReader(new InputStreamReader(fileInputStream));
                //StringBuilder out = new StringBuilder();
                //String line;
                setSavedUserName(reader.readLine());
//                while((savedUserName = reader.readLine()) != null){
////                    savedUserName = Integer.toString(fileInputStream.read());
//                    Log.d("TAG", "SAVED USERNAME: " + savedUserName);
//                }
                reader.close();

            } catch (Exception e) {
                e.printStackTrace();
            }
//            setSavedUserName();
        }
    }

    public void setSavedUserName(String savedUserName){
        this.savedUserName = savedUserName;
        notifySingleBindedItems(BR.savedUserName);
    }

    public String getSavedUserName(){
        return savedUserName;
    }

//    public LiveData<User> getUserLiveData(){
//        return userLiveData;
//    }

//    @BindingAdapter("imageUrl")
//    public static void loadImage(ImageView view, ) {
//        Glide.with(view.getContext())
//                .load(imageUrl)
//                .apply(new RequestOptions().override(400, 400))
//                .into(view);
//    }

    public void initAccount(String passedAccountType, String passedAccountNumber){
        if(passedAccountType.equals("Checking")){
            checkingAccountNumber = passedAccountNumber;
            int accountBalance = new Random().nextInt(100000);
            globalUser.setCheckingAccountNumber(passedAccountNumber);
            globalUser.setCheckingAccount(String.valueOf(accountBalance));
            globalUser.setCheckingAccountAvailable(String.valueOf(new Random().nextInt(accountBalance)));
            Log.d("TAG", "Account Number and Amount Are: " + globalUser.getCheckingAccountNumber()
            + " " + globalUser.getCheckingAccount());
        }
        else if(passedAccountType.equals("Savings")){
            savingsAccountNumber = passedAccountNumber;
            globalUser.setSavingsAccountNumber(passedAccountNumber);
            int accountBalance = new Random().nextInt(100000);
            globalUser.setSavingsAccount(String.valueOf(accountBalance));
            globalUser.setSavingsAccountAvailable(String.valueOf(new Random().nextInt(accountBalance)));
            Log.d("TAG", "Account Number and Amount Are: " + globalUser.getSavingsAccountNumber()
                    + " " + globalUser.getSavingsAccount());
        }
        else{
            Loan loan = new Loan();
            loan.setLoanNumber(passedAccountNumber);
            loan.setLoanAmount(String.valueOf(new Random().nextInt(100000)));
            loanList.add(loan);
            globalUser.setLoans(loanList);
            Log.d("TAG", "Loan Account Number and Amount Are: " + globalUser.getLoans().get(loanList.size()-1).getLoanNumber()
                    + " " + globalUser.getLoans().get(loanList.size()-1).getLoanAmount());
        }
    }

    public LiveData<String> getRetrievedUserName(){
        return retrievedUserNameLiveData;
    }


    public void handleClick(final View view){
        Log.d("TAG", "In handleClick");
        switch (view.getId()){
            case R.id.btnLogIn:
                if(retrievedUserName != null && !retrievedUserName.isEmpty()
                        && retrievedPassword != null && !retrievedPassword.isEmpty()){

                    firebaseAuth.signInWithEmailAndPassword(retrievedUserName, retrievedPassword)
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        // Sign in success, update UI with the signed-in user's information
                                        Log.d("TAG", "signInWithEmail:success");
                                        firebaseUser = firebaseAuth.getCurrentUser();

                                        if(saveId) {
                                            File directory = context.getFilesDir();
                                            if(!Arrays.asList(directory.list()).contains(FILE_NAME)) {
                                                File file = new File(directory, FILE_NAME);
                                                Log.d("TAG", "FILENAME: " + file.getName());
                                            }
                                            else{
                                                List<String> fileList = Arrays.asList(context.getFilesDir().list());
                                                int fileLocation = fileList.indexOf(FILE_NAME);
                                                File file = context.getFilesDir().listFiles()[fileLocation];
                                                file.delete();
                                                File userNameFile = new File(directory, FILE_NAME);
                                            }
                                            FileOutputStream fileOutputStream;
                                            try {
                                                fileOutputStream = context.openFileOutput(FILE_NAME, Context.MODE_PRIVATE);
                                                fileOutputStream.write(retrievedUserName.getBytes());
                                                fileOutputStream.close();
                                            } catch (Exception e) {
                                                e.printStackTrace();
                                            }
                                        }

                                        retrievedUserName = retrievedUserName.replace(".com", "");
                                        retrievedUserNameLiveData.setValue(retrievedUserName);
//                                        Intent intent = new Intent(view.getContext(), AccountsActivity.class);
//                                        intent.putExtra("beautiful_userName", retrievedUserName);
//                                        view.getContext().startActivity(intent);



//                                        List<Loan> loanList = new ArrayList<>();
//                                        Loan loan1 = new Loan("1111", "10000.00");
//                                        Loan loan2 = new Loan("2222", "20000.00");
//                                        Loan loan3 = new Loan("3333", "30000.00");
//                                        loanList.add(loan1);
//                                        loanList.add(loan2);
//                                        loanList.add(loan3);
//                                        List<Transaction> transactionList = new ArrayList<>();
//                                        Transaction transaction1 = new Transaction("Mar 01, 2019", "Taco Bell", "8374y3dhh3",
//                                                "sfh873hh2", "10.00");
//                                        Transaction transaction2 = new Transaction("Mar 02, 2019", "KFC", "2738482",
//                                                "9847980", "27.99");
//                                        Transaction transaction3 = new Transaction("Mar 03, 2019", "Taco Bell", "394859",
//                                                "498280293", "11.34");
//                                        transactionList.add(transaction1);
//                                        transactionList.add(transaction2);
//                                        transactionList.add(transaction3);
//                                        User user = new User(retrievedUserName, "1234", "70000.00", "10000.00",
//                                                "5555", "5000.00", "1000.00",
//                                                loanList, transactionList);
//                                        myRef.child(retrievedUserName).setValue(user);
//
//                                myRef.child(retrievedUserName).child("loans").child("loan1").setValue(loan1);
//                                myRef.child(retrievedUserName).child("loans").child("loan2").setValue(loan2);
//                                myRef.child(retrievedUserName).child("loans").child("loan3").setValue(loan3);
                                    } else {
                                        // If sign in fails, display a message to the user.
                                        Log.w("TAG", "signInWithEmail:failure", task.getException());


                                    }

                                }
                            });

                    //userLiveData.setValue(retrievedUserName);

                }
                break;
            case R.id.tvEnroll:
                Intent intent = new Intent(view.getContext(), SignUpActivity.class);
                view.getContext().startActivity(intent);
                break;
            case R.id.cbSaveID:
                if(saveId){
                    saveId = false;
                }
                else{
                    saveId = true;
                }

        }
    }

    public void createUserAndAddToDatabase(final View view, final String email, String password){
        if(Utilities.isUserEmailValid(email) && Utilities.isPasswordValid(password)){
            firebaseAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information
                                Log.d("TAG", "createUserWithEmail:success");
                                FirebaseUser user = firebaseAuth.getCurrentUser();
                                retrievedUserName = email.replace(".com", "");
                                globalUser.setUserName(retrievedUserName);
                                List<Transaction> transactionList = new ArrayList<>();
                                        Transaction transaction1 = new Transaction("Mar 01, 2019", "Taco Bell", "8374y3dhh3",
                                                "sfh873hh2", "10.00");
                                        Transaction transaction2 = new Transaction("Mar 02, 2019", "KFC", "2738482",
                                                "9847980", "27.99");
                                        Transaction transaction3 = new Transaction("Mar 03, 2019", "Taco Bell", "394859",
                                                "498280293", "11.34");
                                        transactionList.add(transaction1);
                                        transactionList.add(transaction2);
                                        transactionList.add(transaction3);

                                        globalUser.setTransactions(transactionList);
                                myRef.child(retrievedUserName).setValue(globalUser);
                                Intent intent = new Intent(view.getContext(), AccountsActivity.class);
                                intent.putExtra("beautiful_userName", retrievedUserName);
                                view.getContext().startActivity(intent);
                            } else {
                                // If sign in fails, display a message to the user.
                                Log.w("TAG", "createUserWithEmail:failure", task.getException());

                            }
                        }
                    });
        }
        else{
            Toast.makeText(view.getContext(), "Please enter a valid Email and Password!", Toast.LENGTH_LONG).show();
        }

    }


    public void afterAccountNumberChanged(CharSequence charSequence){
        String input = charSequence.toString();
        if(Utilities.isAccountNumberValid(input)){
            setAccountNumberValidStatus(Constants.VALID_ACCOUNT);
            if(selectedItemFromSpinner.equals("Checking")){
                checkingAccountNumber = input;
            }
            else if(selectedItemFromSpinner.equals("Savings")){
                savingsAccountNumber = input;
            }
            else{
                loanAccountNumber = input;
            }

        }
        else{
            setAccountNumberValidStatus(Constants.INVALID_ACCOUNT);
        }

    }

    public void setAccountNumberValidStatus(String accountNumberValidStatus){
        this.accountNumberValidStatus = accountNumberValidStatus;
        notifySingleBindedItems(BR.accountNumberValidStatus);
    }

    public void onSelectItem(AdapterView<?> parent, View view, int pos, long id) {
        selectedItemFromSpinner = (String) parent.getAdapter().getItem(pos);
        Log.d("TAG", "ITEM SELECTED FROM SPINNER: " + selectedItemFromSpinner);
        //accountNumLiveData.setValue(tempAccountType);
    }

    public LiveData<String> getAccountType(){
        return accountNumLiveData;
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
            Log.d("TAG", "Username: " + retrievedUserName);
        }
        else if(!charSequence.toString().isEmpty()){
            setEmailValidStatus(Constants.INVALID_EMAIL);

        }
    }

    public String getAccountNumberValidStatus(){
        return accountNumberValidStatus;
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
