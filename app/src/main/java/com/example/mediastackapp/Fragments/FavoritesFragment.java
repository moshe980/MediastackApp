package com.example.mediastackapp.Fragments;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.mediastackapp.Activities.ArticleActivity;
import com.example.mediastackapp.Data.Article;
import com.example.mediastackapp.Adapters.ArticlesListAdapter;
import com.example.mediastackapp.R;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;


public class FavoritesFragment extends Fragment {
    private ArrayList<Article> favoritesList;
    private Set<Article> favoritesSet;
    private ArticlesListAdapter.OnItemClickListener mlistener;
    private RecyclerView mRecyclerView;
    public static ArticlesListAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private SharedPreferences sp;
    private FirebaseAuth mAuth;
    private GoogleSignInClient mGoogleSignInClient;
    private ActivityResultLauncher<Intent> mGetContent;
    private Button signInBtn;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_favorites, container, false);

        signInBtn = view.findViewById(R.id.sign_in_btn);
        mRecyclerView = view.findViewById(R.id.favorites_recyclerView);

        mAuth = FirebaseAuth.getInstance();

        // Configure Google Sign In
        GoogleSignInOptions gso = new GoogleSignInOptions
                .Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken("588641862986-j531mrf2j9o2fbjb5smq5ca4ovjfqlcvs.apps.googleusercontent.com")
                .requestEmail()
                .build();

        // Build a GoogleSignInClient with the options specified by gso.
        mGoogleSignInClient = GoogleSignIn.getClient(getContext(), gso);

        Intent signIntent = mGoogleSignInClient.getSignInIntent();
        mGetContent = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
            @Override
            public void onActivityResult(ActivityResult result) {
                signInBtn.setVisibility(View.GONE);
                mRecyclerView.setVisibility(View.VISIBLE);


            }
        });
        signInBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mGetContent.launch(signIntent);

            }
        });

        mRecyclerView = view.findViewById(R.id.favorites_recyclerView);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(getContext());
        favoritesList = new ArrayList<Article>();
        favoritesSet = new HashSet<Article>();
        mAdapter = new ArticlesListAdapter(favoritesList);

        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);

        return view;
    }

    private void firebaseAuthWithGoogle(String idToken) {
        AuthCredential credential = GoogleAuthProvider.getCredential(idToken, null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener((Activity) getContext(), new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            signInBtn.setVisibility(View.GONE);
                            mRecyclerView.setVisibility(View.VISIBLE);
                            Toast.makeText(getActivity(), "Login succeed!", Toast.LENGTH_SHORT).show();

                        } else {
                            signInBtn.setVisibility(View.VISIBLE);
                            mRecyclerView.setVisibility(View.GONE);

                            Toast.makeText(getActivity(), "Login Failed!", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    @Override
    public void onResume() {
        super.onResume();
        sp = getActivity().getApplicationContext().getSharedPreferences("favorites", Context.MODE_PRIVATE);
        favoritesSet.clear();
        favoritesList.clear();

        String serializedObject = sp.getString("a", null);
        if (serializedObject != null && !serializedObject.isEmpty()) {
            String[] separted = serializedObject.split("&");
            Gson gson = new Gson();
            for (int i = separted.length - 1; i >= 0; i--) {
                Article article = gson.fromJson(separted[i], Article.class);
                favoritesSet.add(article);
            }
            favoritesList.addAll(favoritesSet);
            mAdapter = new ArticlesListAdapter(favoritesList);

            mAdapter.setOnItemClickListener(new ArticlesListAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(int position) {
                    Intent intent = new Intent(getContext(), ArticleActivity.class);
                    Article article = favoritesList.get(position);
                    intent.putExtra("article", article);
                    startActivity(intent);

                }
            });

            mRecyclerView.setLayoutManager(mLayoutManager);
            mRecyclerView.setAdapter(mAdapter);


        } else {
            mRecyclerView.setLayoutManager(mLayoutManager);
            mRecyclerView.setAdapter(mAdapter);

        }
    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {

        }
    }
}
