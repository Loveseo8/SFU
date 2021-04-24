package com.alicearnautova;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

public class MainActivity extends AppCompatActivity {

    ListView listView;
    ListViewAdapter adapter;
    List<String> elements = new ArrayList<>();
    FirebaseStorage storage = FirebaseStorage.getInstance();
    StorageReference storageRef = storage.getReference();
    StorageReference pathReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        pathReference = storageRef.child("List.xml");

        final File localFile;
        try {
            localFile = File.createTempFile("List", ".xml");

            pathReference.getFile(localFile).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {

                    try {
                        DocumentBuilder documentBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
                        Document document = documentBuilder.parse(localFile);

                        Node root = document.getDocumentElement();
                        NodeList tasks = root.getChildNodes();
                        for (int i = 0; i < tasks.getLength(); i++) {
                            Node task = tasks.item(i);
                            if (task.getNodeType() != Node.TEXT_NODE) {
                                NodeList bookProps = task.getChildNodes();
                                for(int j = 0; j < bookProps.getLength(); j++) {
                                    Node bookProp = bookProps.item(j);
                                    if (bookProp.getNodeType() != Node.TEXT_NODE) {

                                        if(bookProp.getNodeName().equals("Title")) elements.add(bookProp.getChildNodes().item(0).getTextContent());
                                        Log.d("Taga", bookProp.getChildNodes().item(0).getTextContent());
                                    }
                                }
                            }
                        }

                    } catch (ParserConfigurationException ex) {
                        ex.printStackTrace(System.out);
                    } catch (SAXException ex) {
                        ex.printStackTrace(System.out);
                    } catch (IOException ex) {
                        ex.printStackTrace(System.out);
                    }
                }

            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception exception) {
                }
            }).addOnProgressListener(new OnProgressListener<FileDownloadTask.TaskSnapshot>() {
                @Override
                public void onProgress(FileDownloadTask.TaskSnapshot taskSnapshot) {
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        listView = findViewById(R.id.list_view);
        adapter = new ListViewAdapter(this, elements);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i = new Intent(MainActivity.this, ElementActivity.class);
                i.putExtra("ElementTitle", elements.get(position));
                startActivity(i);
            }
        });
    }
}