package com.bung.bungapp.util;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.bung.bungapp.PartyActivity;
import com.bung.bungapp.R;
import com.bung.bungapp.RecruitingActivity;
import com.bung.bungapp.core.ApiCaller;
import com.bung.bungapp.core.Callback;
import com.bung.bungapp.model.ChickenStoreInformation;
import com.bung.bungapp.model.Party;

import me.drakeet.materialdialog.MaterialDialog;

/**
 * Created by rok on 2015. 11. 15..
 */
public class DialogUtils {
    static public void openChickenPopup(Context context, String placeId) {
        ApiCaller.getChickenStoreInformation(placeId, new Callback<ChickenStoreInformation>() {
            @Override
            public void onSuccess(ChickenStoreInformation result) {
                if (result != null && result.getResult() != null) {
                    ChickenStoreInformation.ResultEntity entity = result.getResult();
                    View contentView = View.inflate(context, R.layout.dialog_chicken, null);
                    MaterialDialog dialog = new MaterialDialog(context).setView(contentView);

                    TextView title = (TextView) contentView.findViewById(R.id.title);
                    TextView tel = (TextView) contentView.findViewById(R.id.telecom);
                    TextView location = (TextView) contentView.findViewById(R.id.location);
                    ListView listview = (ListView) contentView.findViewById(R.id.listview);
                    Button raidButton = (Button) contentView.findViewById(R.id.button_raid);
                    Button closeButton = (Button) contentView.findViewById(R.id.button_close);

                    title.setText(entity.getName());
                    tel.setText(entity.getFormatted_phone_number());
                    location.setText(entity.getFormatted_address());

                    raidButton.setOnClickListener(v -> {
                        Intent intent = new Intent(context, RecruitingActivity.class);
                        intent.putExtra(RecruitingActivity.ARG_PLACE_ID, entity.getPlace_id());
                        intent.putExtra(RecruitingActivity.ARG_PLACE_NAME, entity.getName());
                        intent.putExtra(RecruitingActivity.ARG_PLACE_LATLNG, entity.getGeometry().getLocation().getLatLng());
                        context.startActivity(intent);

                    });
                    closeButton.setOnClickListener(v -> {
                        dialog.dismiss();
                    });

                    if (entity.getReviews() != null) {
                        listview.setAdapter(new BaseAdapter() {

                            @Override
                            public int getCount() {
                                return entity.getReviews().size();
                            }

                            @Override
                            public ChickenStoreInformation.ResultEntity.ReviewsEntity getItem(int position) {
                                return entity.getReviews().get(position);
                            }

                            @Override
                            public long getItemId(int position) {
                                return entity.getReviews().get(position).hashCode();
                            }

                            @Override
                            public View getView(int position, View convertView, ViewGroup parent) {
                                if (convertView == null) {
                                    convertView = View.inflate(context, R.layout.row_review, null);
                                }
                                ImageView image = (ImageView) convertView.findViewById(R.id.profile_icon);
                                TextView name = (TextView) convertView.findViewById(R.id.name);
                                TextView timestamp = (TextView) convertView.findViewById(R.id.timestamp);
                                TextView review = (TextView) convertView.findViewById(R.id.review);

                                ChickenStoreInformation.ResultEntity.ReviewsEntity item = getItem(position);
                                String photoUrl = item.getProfile_photo_url();
                                if (photoUrl != null) {
                                    if (photoUrl.startsWith("//")) {
                                        photoUrl = "http:" + photoUrl;
                                    }
                                    if (image != null) {
                                        Glide.with(context).load(photoUrl).asBitmap().centerCrop().placeholder(R.drawable.person_image_empty).into(new BitmapImageViewTarget(image) {
                                            @Override
                                            protected void setResource(Bitmap resource) {
                                                RoundedBitmapDrawable circularBitmapDrawable =
                                                        RoundedBitmapDrawableFactory.create(context.getResources(), resource);
                                                circularBitmapDrawable.setCircular(true);
                                                image.setImageDrawable(circularBitmapDrawable);
                                            }
                                        });
                                    }
                                } else {
                                    image.setImageResource(R.drawable.person_image_empty);
                                }
                                name.setText(item.getAuthor_name());
                                timestamp.setText(String.valueOf(item.getRating()) + "점");
                                review.setText(item.getText());

                                return convertView;
                            }
                        });
                    }
                    dialog.show();
                }
            }

            @Override
            public void onFailure() {

            }
        });
    }

    static public void openPartyPopup(Context context, Party party) {
        View contentView = View.inflate(context, R.layout.dialog_party, null);
        MaterialDialog dialog = new MaterialDialog(context).setView(contentView);

        TextView title = (TextView) contentView.findViewById(R.id.title);
        TextView owner = (TextView) contentView.findViewById(R.id.owner);
        TextView description = (TextView) contentView.findViewById(R.id.description);
        ListView listview = (ListView) contentView.findViewById(R.id.listview);
        Button raidButton = (Button) contentView.findViewById(R.id.button_raid);
        Button closeButton = (Button) contentView.findViewById(R.id.button_close);

        title.setText(party.getName());
        owner.setText(party.getCreator().getName());
        description.setText(party.getDescription());

        raidButton.setOnClickListener(v -> {
            Intent intent = new Intent(context, PartyActivity.class);
            context.startActivity(intent);
            dialog.dismiss();
        });
        closeButton.setOnClickListener(v -> {
            dialog.dismiss();
        });

        dialog.show();
    }
}
