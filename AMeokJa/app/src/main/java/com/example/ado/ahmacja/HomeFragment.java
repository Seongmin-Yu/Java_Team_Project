package com.example.ado.ahmacja;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;

public class HomeFragment extends Fragment {
    public HomeFragment() {

    }
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        FrameLayout layout = (FrameLayout) inflater.inflate(R.layout.fragment_home, container, false);
        TextView editTextHeme = (TextView)layout.findViewById(R.id.editText_home);
        Typeface customFont = Typeface.createFromAsset(getActivity().getAssets(),  "서울남산 장체M.ttf");
        editTextHeme.setTypeface(customFont);
        editTextHeme.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), SerchActivity.class);
                startActivity(intent);
            }
        });
        return layout;
    }
}
