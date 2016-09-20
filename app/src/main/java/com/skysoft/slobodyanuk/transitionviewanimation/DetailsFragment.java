package com.skysoft.slobodyanuk.transitionviewanimation;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Range;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by Serhii Slobodyanuk on 19.09.2016.
 */
public class DetailsFragment extends Fragment {

    private Unbinder unbinder;

    @BindView(R.id.root)
    LinearLayout mRoot;
    private float dy;

    public static DetailsFragment newInstance() {
        Bundle args = new Bundle();
        DetailsFragment fragment = new DetailsFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_item, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        final FrameLayout.LayoutParams parms = (FrameLayout.LayoutParams) mRoot.getLayoutParams();
        final int startY = (int) mRoot.getY();
        final Range<Integer> range = Range.create(0, 280);
        final int halfSwipe = (int) (range.getUpper() * 0.7);
        mRoot.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()){
                    case MotionEvent.ACTION_DOWN:
                        dy = event.getRawY() - parms.topMargin;
                        break;
                    case MotionEvent.ACTION_MOVE:
                        float y = event.getRawY();
                        parms.topMargin = (int) (y - dy);
                        if (range.contains(parms.topMargin)) {
                            mRoot.setLayoutParams(parms);
                        }
                        break;
                    case MotionEvent.ACTION_UP:
                        if (parms.topMargin >= halfSwipe) {
                            getActivity().onBackPressed();
                        } else {
                            parms.topMargin = startY;
                            mRoot.setLayoutParams(parms);
                        }
                        break;
                }
                return true;
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

}
