package com.licathryn.coreproficiencieslab;

import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.android.volley.toolbox.Volley;
import com.android.volley.RequestQueue;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;


public class fragment extends Fragment implements View.OnClickListener {

    private Button submitButton;
    private TextView gender1;
    private TextView gender2;
    private TextView gender3;
    private RadioGroup poll_group;
    private int poll_answer;

    private static final String TAG = "MY_FRAGMENT";
    public fragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_my, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        poll_group = view.findViewById(R.id.poll_container);
        submitButton = view.findViewById(R.id.btn_get);
        submitButton.setOnClickListener(this);
        gender1 = view.findViewById(R.id.gender1);
        gender2 = view.findViewById(R.id.gender2);
        gender3 = view.findViewById(R.id.gender3);

    }

    @Override
    public void onClick(View v) {
        switch(poll_group.getCheckedRadioButtonId()) {
            case R.id.radio_male:
                poll_answer = 1;
                break;
            case R.id.radio_female:
                poll_answer = 2;
                break;
            case R.id.radio_other:
                poll_answer = 3;
                break;
        }
        Uri.Builder builder = new Uri.Builder();
        builder.scheme("https")
                .authority("core-proficiencies-lab.sites.tjhsst.edu")
                .appendPath("volley")
                .appendQueryParameter("gender", String.valueOf(poll_answer));
        String url = builder.build().toString();
        MyResponseHelper myResponseHelper = new MyResponseHelper();
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, myResponseHelper, myResponseHelper);
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(stringRequest);
    }


    protected class MyResponseHelper implements Response.Listener<String>, Response.ErrorListener {
        @Override
        public void onResponse(String response) {
            Gson gson = new GsonBuilder().create();
            myData[] data = gson.fromJson(response, myData[].class);
            System.out.println(data);

            gender1.setText( data[0].gender + ": " + data[0].votes );
            gender2.setText( data[1].gender + ": " + data[1].votes );
            gender3.setText( data[2].gender + ": " + data[2].votes );
        }

        @Override
        public void onErrorResponse(VolleyError error) {
            Toast.makeText(getContext(), error.toString(), Toast.LENGTH_LONG).show();
        }
    }
}
