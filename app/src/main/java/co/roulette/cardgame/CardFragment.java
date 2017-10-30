package co.roulette.cardgame;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

public class CardFragment extends Fragment {
    private Card mCard;
    private ViewHolder mView;

    public final static String CARD_KEY = "cardKey";
    @Override
    public void setArguments(Bundle args) {
        super.setArguments(args);
        mCard = args.getParcelable(CARD_KEY);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = new ViewHolder(inflater.inflate(R.layout.fragment_card, container, false));
        return mView.rootView;
    }

    @Override
    public void onStart() {
        super.onStart();
        getActivity().runOnUiThread(()->{
            mView.cardView.setImageDrawable(mCard.getImage(getActivity()));
        });
    }

    @Override
    public void onDestroy() {
        getActivity().runOnUiThread(()->{
            mView.cardView.setImageDrawable(null);
        });
        super.onDestroy();
    }

    private class ViewHolder {
        View rootView;
        ImageView cardView;
        ViewHolder(View v) {
            rootView = v;
            cardView = (ImageView)v.findViewById(R.id.card_image);
        }
    }
}
