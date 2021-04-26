package com.alicearnautova;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

public class PresentationsActivity extends AppCompatActivity {

    ListView listView;
    ListViewAdapter adapter;
    List<String> elements = new ArrayList<>();
    FirebaseStorage storage = FirebaseStorage.getInstance();
    StorageReference storageRef = storage.getReference();
    StorageReference pathReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.presentations_activity);

        getSupportActionBar().setTitle("Презентации институтов");


        DocumentBuilder documentBuilder = null;
        try {
            documentBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        }
        Document document = null;
        try {
            document = documentBuilder.parse(getAssets().open("Presentations.xml"));
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        }

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

        listView = findViewById(R.id.list_view);
        adapter = new ListViewAdapter(this, elements);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i = new Intent(PresentationsActivity.this, ShowPresentationActivity.class);
                i.putExtra("PresentationTitle", elements.get(position));
                startActivity(i);
            }
        });
    }
}
