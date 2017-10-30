package co.roulette.cardgame;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;

import java.util.List;

public class CardAdapter extends FragmentStatePagerAdapter {

    private Context mContext;
    private List<CardFragment> mCards;

    public CardAdapter(Context c, List<CardFragment> cards, FragmentManager fm) {
        super(fm);
        mContext = c;
        mCards = cards;
    }

    public void updateList(List<CardFragment> cards){
        mCards = cards;
        notifyDataSetChanged();
    }

    // https://stackoverflow.com/questions/10396321/remove-fragment-page-from-viewpager-in-android
    // Tell the adapter that all fragments have moved when the list changes.
    @Override
    public int getItemPosition(Object object){
        return PagerAdapter.POSITION_NONE;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return String.valueOf( position + 1 );
    }

    @Override
    public Fragment getItem(int position) {
        return mCards.get(position);
    }

    @Override
    public int getCount() {
        return mCards.size();
    }
}
