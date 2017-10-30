package co.roulette.cardgame;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.DrawableRes;

public class Card implements Parcelable {
    private @DrawableRes int mImageId = R.drawable.card_001;

    public Card(@DrawableRes int imageId){
        if (imageId == 0) {
            mImageId = R.drawable.card_035;
        }
        mImageId = imageId;
    }

    protected Card(Parcel in) {
        mImageId = in.readInt();
    }

    public static final Creator<Card> CREATOR = new Creator<Card>() {
        @Override
        public Card createFromParcel(Parcel in) {
            return new Card(in);
        }

        @Override
        public Card[] newArray(int size) {
            return new Card[size];
        }
    };

    public Drawable getImage(Context c){
        return c.getDrawable(mImageId);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(mImageId);
    }
}
