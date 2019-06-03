package ui.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.anonymous.cikgood.R;
import com.example.anonymous.cikgood.adapters.ChatAdapter;
import com.example.anonymous.cikgood.config.ServerConfig;
import com.example.anonymous.cikgood.models.Chat;
import com.example.anonymous.cikgood.utils.SessionManager;
import com.google.android.gms.drive.Drive;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class MessageActivity extends AppCompatActivity {

    CircleImageView profile_image;
    TextView username2;
    SessionManager sessionManager;
    FirebaseUser firebaseUser;
    DatabaseReference reference;

    Intent intent;

    Button btn_send;
    EditText text_send;
    private String idUserNow;
    //    public MessageAdapter messageAdapter;
    private ChatAdapter chatAdapter;
    //    private List<Chat> mChat;
    private List<Chat> chats;
    private RecyclerView recyclerView2;
    Context context;
    int userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);
        sessionManager = new SessionManager(MessageActivity.this);
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_chevron);
        toolbar.setTitle("");
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        recyclerView2 = findViewById(R.id.recycler_view);
        recyclerView2.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        linearLayoutManager.setStackFromEnd(true);
        recyclerView2.setLayoutManager(linearLayoutManager);

        profile_image = findViewById(R.id.profile_image);
        username2 = findViewById(R.id.username);
        btn_send = findViewById(R.id.btn_send);
        text_send = findViewById(R.id.text_send);

        idUserNow = (sessionManager.getMuridProfile().get("id"));

        intent = getIntent();
        userId = intent.getIntExtra("id", 0);
        final String nama = intent.getStringExtra("nama");
        final String photo = intent.getStringExtra("photo_profile");

        Log.d("kucing photo", ""+photo);

        username2.setText(nama);
//        if (user.getImageURL().equals("default")){
//            profile_image.setImageResource(R.drawable.man);
//
//        }else{
//        Glide.with(MessageActivity.this).load(photo).into(profile_image);
//        }
        Glide.with(MessageActivity.this)
                .load(ServerConfig.GURU_PATH+photo)
                .apply(new RequestOptions().override(100, 100))
                .into(profile_image);

        System.out.println("Id User Now: "+idUserNow);
        System.out.println("Driver id chat: "+userId);
        System.out.println("Username chat: "+nama);
//        System.out.println("Photo chat: "+photo);

        btn_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String msg = text_send.getText().toString();
                if (!msg.equals("")){
                    sendMessage(String.valueOf(idUserNow), String.valueOf(userId), msg);
                }else{
                    Toast.makeText(MessageActivity.this, "You can't send empty message", Toast.LENGTH_SHORT).show();
                }
                text_send.setText("");
            }
        });

        readMessages(idUserNow, String.valueOf(userId));
    }

    private void readMessages(final String myid, final String userid) {
        chats = new ArrayList<>();

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Chats");
        System.out.println("data referencenya:"+reference);
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                System.out.println("data snapshot:"+dataSnapshot);
                chats.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()){

                    Chat chat = snapshot.getValue(Chat.class);

                    System.out.println("Data chat"+snapshot.getValue());

                    Log.d("TAG CHAT", chat.getMessage() + " / " +
                            chat.getReceiver() + " / " +
                            chat.getSender()+" /"+dataSnapshot.getValue());

                    Log.d("Data user sekarang ", ""+idUserNow);
                    Log.d("Data user dipilih ", ""+userid);
                    Log.d("Data getReceiver ", ""+chat.getReceiver());
                    Log.d("Data getSender ", ""+chat.getSender());

                    Log.d("IF PERTAMA", chat.getReceiver() + " = " +
                            myid + " && " +
                            chat.getSender()+" = "+userid);

                    Log.d("IF KEDUA", chat.getReceiver() + " = " +
                            userid + " && " +
                            chat.getSender()+" = "+myid);


                    if (chat.getReceiver().equals(myid) && chat.getSender().equals(userid) ||
                            chat.getReceiver().equals(userid) && chat.getSender().equals(myid)){
                        Log.d("kucing", ""+userid);
                        chats.add(chat);
                    }

                    chatAdapter = new ChatAdapter(MessageActivity.this, chats, myid);
                    recyclerView2.setAdapter(chatAdapter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.d("Cancelled", databaseError.toString() );

            }
        });
    }


    private void sendMessage(String sender, String receiver, String message){

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();

        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("sender", sender);
        hashMap.put("receiver", receiver);
        hashMap.put("message", message);

        reference.child("Chats").push().setValue(hashMap);

        final DatabaseReference chatRef = FirebaseDatabase.getInstance().getReference("Chatlist")
                .child(idUserNow)
                .child(String.valueOf(userId));

        chatRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (!dataSnapshot.exists()){
                    String id = String.valueOf(userId);
                    chatRef.child("id").setValue(id);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        final DatabaseReference chatRef2 = FirebaseDatabase.getInstance().getReference("Chatlist2")
                .child(String.valueOf(userId))
                .child(idUserNow);

        chatRef2.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (!dataSnapshot.exists()){
                    String id = String.valueOf(userId);
                    chatRef2.child("id").setValue(id);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }


}
