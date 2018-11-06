package com.example.admin.quanlysach;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.admin.quanlysach.R;
import com.example.admin.quanlysach.adapter.AboutAdapter;
import com.example.admin.quanlysach.model.About;

import java.util.ArrayList;
import java.util.List;

public class AboutFragment extends Fragment {
    private List<About> aboutList;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_about,container,false);
        RecyclerView lvListIntro = view.findViewById(R.id.lvListIntro);
        aboutList = new ArrayList<>();
        fakeDate();
        AboutAdapter aboutAdapter = new AboutAdapter(aboutList);
        lvListIntro.setAdapter(aboutAdapter);
        RecyclerView.LayoutManager manager = new LinearLayoutManager(getActivity());
        lvListIntro.setLayoutManager(manager);
        return view;
    }
    private void fakeDate(){
        About about = new About("Book Manager","Application");
        aboutList.add(about);
        About about1 = new About("Beta 0.0.1","Version");
        aboutList.add(about1);
        About about2 = new About("A good app for the huge book store system, suitable for the book puplishers and book store masters.","Describe");
        aboutList.add(about2);
        About about3 = new About("Book management, bill management, statistics by day, easy to use","Uses");
        aboutList.add(about3);
        About about4 = new About("RK31597-FPTP-HN151018","App ID");
        aboutList.add(about4);
    }
}
