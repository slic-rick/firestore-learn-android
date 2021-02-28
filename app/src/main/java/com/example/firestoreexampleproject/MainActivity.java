package com.example.firestoreexampleproject;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.ListenerRegistration;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.SetOptions;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    private static final String KEY_TITLE = "title";
    private static final String KEY_DESCRIPTION = "description";

    private EditText title,description,priority,tags;
    private TextView loadData;

    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference noteBoookRef = db.collection("Notebook");
    private DocumentReference noteRef = db.document("Notebook/My first note");
    //private ListenerRegistration listenerRegistration;                                            // to de attach the eventListener

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        title = (EditText) findViewById(R.id.editText_title);
        description = (EditText) findViewById(R.id.editText_description);
        priority = (EditText) findViewById(R.id.editText_priority);
        loadData = (TextView) findViewById(R.id.textView_text);

        tags = (EditText) findViewById(R.id.editText_tags);


    }


//    @Override
//    protected void onStart() {
//        super.onStart();
//
//        noteBoookRef.addSnapshotListener(this, new EventListener<QuerySnapshot>() {
//            @Override
//            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
//                if (e != null){
//                 return;                                                                            // exit the method
//                }
//
//                String data = "";
//
//                for (QueryDocumentSnapshot queryDocumentSnapshot: queryDocumentSnapshots){
//                    Note note = queryDocumentSnapshot.toObject(Note.class);
//
//                    note.setNoteID(queryDocumentSnapshot.getId());
//                    String title = note.getTitle();
//                    String description = note.getDescription();
//                    String id = note.getNoteID();
//                    int priority = note.getPriority();
//
//                    data += "ID: "+ id+ " \nTitle: "+ title + "\ndescription: " + description+"\nPriority: "+priority +"\n\n";
//                }
//                loadData.setText(data);
//                //noteBoookRef.document(id).update("note");                                         // referencing a particular note
//            }
//        });
//
//    }


    public void saveNote(View view) {
        String title = this.title.getText().toString();
        String description = this.description.getText().toString();

        if (this.priority.length() == 0){
            priority.setText("0");
        }

        int priority = Integer.parseInt(this.priority.getText().toString());
        String tagsInput =tags.getText().toString();

        String[] tagArray = tagsInput.split("\\s*,\\s*");
        Map<String,Boolean> tagsList = new HashMap<>();
        Note note = new Note(title,description,priority,tagsList);                                  // turning the array into a list

        for (String tag: tagArray){
            tagsList.put(tag,true);
        }

        noteBoookRef.document("0tq5CD6wgYFoBLvTmY7i").collection("Sub collection").add(note);

//        noteBoookRef.add(note).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
//            @Override
//            public void onSuccess(DocumentReference documentReference) {
//                Toast.makeText(getApplicationContext(),"Added successfully",Toast.LENGTH_LONG).show();
//            }
//        });

    }


//    public void description(View view) {
//        String description = this.description.getText().toString();
//        Map<String,Object> descrip = new HashMap<>();
//
//        descrip.put(KEY_DESCRIPTION, FieldValue.delete());
//
//        noteRef.update(descrip);                                                                    // if the object is already existing,it will override
//    }

    public void loadNote(View view) {
        //getting query from database
        noteBoookRef.document("0tq5CD6wgYFoBLvTmY7i").collection("Sub collection").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                String data = "";

                for (QueryDocumentSnapshot queryDocumentSnapshot: queryDocumentSnapshots){
                    Note note = queryDocumentSnapshot.toObject(Note.class);

                    note.setNoteID(queryDocumentSnapshot.getId());
                   // String title = note.getTitle();
                   // String description = note.getDescription();
                    String id = note.getNoteID();

                    data +="ID: "+id;

                    for (String tag: note.getTags().keySet()){                                     //returning the keys of a hashMap,(Tag strings)
                        data +="\n- "+tag;
                    }
//                    int priority = note.getPriority();

                    data += "\n\n";

                }
                loadData.setText(data);

            }
        });
    }

//    public void viewNote(View view) {
//        noteRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
//            @Override
//            public void onSuccess(DocumentSnapshot documentSnapshot) {
//                if (documentSnapshot.exists()){
//                    String title = documentSnapshot.getString(KEY_TITLE);
//                    String description = documentSnapshot.getString(KEY_DESCRIPTION);
//
//                    loadData.setText("Title: "+ title + "\n" + "description: " + description);
//                } else {
//
//
//                }
//            }
//        }).addOnFailureListener(new OnFailureListener() {
//            @Override
//            public void onFailure(@NonNull Exception e) {
//                Toast.makeText(getApplicationContext(),"Error",Toast.LENGTH_LONG).show();
//                Log.d(TAG, "onFailure: "+e.getMessage());
//
//            }
//        });
//    }
}
