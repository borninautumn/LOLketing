package com.ezen.lolketing.Fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ezen.lolketing.LeagueInfoActivity;
import com.ezen.lolketing.R;


public class OutlineFragment extends Fragment {
    LeagueInfoActivity leagueInfoActivity;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup rootView =
                (ViewGroup) inflater.inflate(R.layout.fragment_outline, container, false);
        return rootView;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        leagueInfoActivity = (LeagueInfoActivity) getActivity();
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

}
