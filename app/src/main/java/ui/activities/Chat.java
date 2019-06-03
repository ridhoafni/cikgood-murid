package ui.activities;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.example.anonymous.cikgood.R;
import com.example.anonymous.cikgood.adapters.UserAdapter;
import com.example.anonymous.cikgood.config.ServerConfig;
import com.example.anonymous.cikgood.models.Chatlist;
import com.example.anonymous.cikgood.models.Guru;
import com.example.anonymous.cikgood.models.Matpel;
import com.example.anonymous.cikgood.models.User;
import com.example.anonymous.cikgood.response.ResponseGuru;
import com.example.anonymous.cikgood.response.ResponseMatpel;
import com.example.anonymous.cikgood.rests.ApiClient;
import com.example.anonymous.cikgood.rests.ApiInterface;
import com.example.anonymous.cikgood.utils.SessionManager;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Chat extends AppCompatActivity {
    private RecyclerView recyclerView;
    List<User> mUsers;
    List<User> user;
    SessionManager sessionManager;
    DatabaseReference reference;
    UserAdapter userAdapter;
    ApiInterface apiService;
    String a;
    private ShimmerFrameLayout mShimmerViewContainer;

    private List<Chatlist> usersList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chat);
        mShimmerViewContainer = findViewById(R.id.shimmer_view_container);
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_chevron);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(Chat.this));

        usersList = new ArrayList<>();
        user = new ArrayList<>();

        apiService = ApiClient.getClient(ServerConfig.API_ENDPOINT).create(ApiInterface.class);

        sessionManager = new SessionManager(this);

        reference = FirebaseDatabase.getInstance().getReference("Chatlist").child(sessionManager.getMuridProfile().get(SessionManager.ID_MURID));
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                usersList.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                    Chatlist chatlist = snapshot.getValue(Chatlist.class);
                    usersList.add(chatlist);
                    // method chat list
                }
                chatList();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    private void chatList() {
        mUsers = new ArrayList<>();

        apiService.guruFindAll().enqueue(new Callback<ResponseGuru>() {
            @Override
            public void onResponse(Call<ResponseGuru> call, Response<ResponseGuru> response) {
                if (response.isSuccessful()) {
//                    mUsers.clear();
                    if (response.body().getMaster().size() > 0){
                       user = response.body().getMaster();

                        Iterator<User> iterator = new ArrayList<>(user).iterator();
                        while (iterator.hasNext()) {
                            User user1 = iterator.next();
                            a = String.valueOf(user1.getIdGuru());
                            for (Chatlist chatlist : usersList)
                            {
                                String b = String.valueOf(user1.getIdGuru());
                                boolean c = b.equals(chatlist.getId());
                                System.out.println("data a adalah"+c);

                                if (c)
                                {

                                    Log.d("log user", ""+user1.getIdGuru());
                                    Log.d("log chat", ""+chatlist.getId());
                                    mUsers.add(user1);

                                }
                            }
                        }

                        userAdapter = new UserAdapter(Chat.this, mUsers);
                        recyclerView.setAdapter(userAdapter);


                    } else {
//                    loading.dismiss();
//                    Toast.makeText(mContext, "Gagal mengambil data dosen", Toast.LENGTH_SHORT).show();
                    }
                    // Stopping Shimmer Effect's animation after data is loaded to ListView
                    mShimmerViewContainer.stopShimmer();
                    mShimmerViewContainer.setVisibility(View.GONE);
                    }

            }

            @Override
            public void onFailure(Call<ResponseGuru> call, Throwable t) {
//                loading.dismiss();
                Toast.makeText(Chat.this, "Koneksi internet bermasalah", Toast.LENGTH_SHORT).show();
            }
        });

//        reference = FirebaseDatabase.getInstance().getReference("Users");
//        reference.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                mUsers.clear();
//                for (DataSnapshot snapshot : dataSnapshot.getChildren()){
//                    User user = snapshot.getValue(User.class);
//                    for (Chatlist chatlist : usersList){
//                        if (user.getId().equals(chatlist.getId())){
//                            mUsers.add(user);
//                        }
//                    }
//                }
//                userAdapter = new UserAdapter(getContext(), mUsers);
//                recyclerView.setAdapter(userAdapter);
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//            }
//        });
    }
    @Override
    public void onResume() {
        super.onResume();
        mShimmerViewContainer.startShimmer();
    }

    @Override
    public void onPause() {
        mShimmerViewContainer.stopShimmer();
        super.onPause();
    }
}
