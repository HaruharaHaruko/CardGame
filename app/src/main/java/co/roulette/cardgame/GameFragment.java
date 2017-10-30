package co.roulette.cardgame;

import android.content.res.TypedArray;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class GameFragment extends Fragment {

    enum Difficulty {
        EASY,
        MEDIUM,
        HARD, //hehe...hard...
    }

    private Difficulty mDifficulty = Difficulty.EASY;
    private ViewHolder mView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = new ViewHolder(inflater.inflate(R.layout.fragment_game, container, false));
        return mView.rootView;
    }

    private List<CardFragment> mDeck = new ArrayList<>();
    private List<CardFragment> mDrawn = new ArrayList<>();
    @Override
    public void onStart() {
        super.onStart();

        TypedArray imgs = getResources().obtainTypedArray(R.array.cards);

        for (int i = 0; i < imgs.length(); i++) {

            CardFragment f = new CardFragment();

            Bundle b  = new Bundle();
            b.putParcelable(f.CARD_KEY, new Card(imgs.getResourceId(i, 0)));
            f.setArguments(b);

            mDeck.add(f);
        }

        final CardAdapter adapter = new CardAdapter(getActivity(), mDeck, getFragmentManager());
        mView.pager.setAdapter(adapter);
        mView.tabs.setupWithViewPager(mView.pager);
        mView.tabs.setTabMode(TabLayout.MODE_SCROLLABLE);

        mView.drawButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mDrawn.add(drawCard());
                getActivity().runOnUiThread(()-> adapter.updateList(mDrawn));

            }
        });

        mView.discardButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().runOnUiThread(()->{

                    int index = mView.pager.getCurrentItem();

                    if ( adapter.getCount() == 1 ) {
                        //Last card?
                        Toast.makeText(getActivity(), "YOU WON!", Toast.LENGTH_LONG).show();
                    } else if (adapter.getCount() == 0) {
                        Toast.makeText(getActivity(), "Draw more cards to keep playing!", Toast.LENGTH_LONG).show();
                        return;
                    }

                    mDrawn.remove(index);
                    adapter.updateList(mDrawn);
                    //Select the previous card unless we're at the beginning
                    mView.pager.setCurrentItem( index == 0 ?  0 : index - 1);
                });
            }
        });

        int drawCount = 0;
        switch (mDifficulty) {
            case EASY:
                drawCount = 5;
                break;
            case MEDIUM:
                drawCount = 10;
                break;
            case HARD:
                drawCount = 15;
                break;
        }

        for (int i = 0; i < drawCount; i++) {
            mView.drawButton.callOnClick();
        }
    }

    private CardFragment drawCard() {
        int randomNum = ThreadLocalRandom.current().nextInt(0, mDeck.size());
        return mDeck.get(randomNum);
    }

    private class ViewHolder {
        View rootView;
        TabLayout tabs;
        ViewPager pager;
        FloatingActionButton drawButton;
        FloatingActionButton discardButton;
        ViewHolder(View v) {
            rootView = v;
            tabs = (TabLayout)v.findViewById(R.id.card_tabs);
            pager = (ViewPager)v.findViewById(R.id.card_view_pager);
            drawButton = (FloatingActionButton)v.findViewById(R.id.draw_button);
            discardButton = (FloatingActionButton)v.findViewById(R.id.discard_button);
        }
    }
}
