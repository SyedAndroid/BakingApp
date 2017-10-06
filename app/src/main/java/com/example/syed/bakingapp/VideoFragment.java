package com.example.syed.bakingapp;


import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.LoadControl;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.extractor.ExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.AdaptiveVideoTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelection;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.BandwidthMeter;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;
import com.squareup.picasso.Picasso;


public class VideoFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "url";
    private static final String ARG_PARAM2 = "desc";
    SimpleExoPlayer player;
    SimpleExoPlayerView simpleExoPlayerView;
    TextView descTextView;
    Button fullscreen;
    TextView noVideo;
    long position;
    Uri mp4VideoUri;
    MediaSource videoSource;
    // TODO: Rename and change types of parameters
    private String videoURL;
    private String desc;
    private String imageURL;

    public VideoFragment() {
        // Required empty public constructor
    }

    public void setVariables(String videoURL, String desc, String img) {
        this.videoURL = videoURL;
        this.desc = desc;
        this.imageURL = img;
    }


    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        position = C.TIME_UNSET;
        if (savedInstanceState != null) {
            position = savedInstanceState.getLong("position", C.TIME_UNSET);
        }

        // Inflate the layout for this fragment
        View rootview = inflater.inflate(R.layout.fragment_video, container, false);

        simpleExoPlayerView = (SimpleExoPlayerView) rootview.findViewById(R.id.simpleExo);
        descTextView = (TextView) rootview.findViewById(R.id.desc);
        noVideo = (TextView) rootview.findViewById(R.id.no_video);
        fullscreen = (Button) rootview.findViewById(R.id.exo_fullscreen_button);
        ImageView imageView = (ImageView) rootview.findViewById(R.id.step_image);
        noVideo.setVisibility(View.GONE);
        imageView.setVisibility(View.GONE);
        simpleExoPlayerView.setVisibility(View.GONE);
        fullscreen.setVisibility(View.GONE);

        if (videoURL != null) {
            simpleExoPlayerView.setVisibility(View.VISIBLE);
            Handler mainHandler = new Handler();
            BandwidthMeter bandwidthMeter = new DefaultBandwidthMeter();
            LoadControl loadControl = new DefaultLoadControl();

            TrackSelection.Factory videoTrackSelectionFactory =
                    new AdaptiveVideoTrackSelection.Factory(bandwidthMeter);
            TrackSelector trackSelector = new DefaultTrackSelector();
            Context context = getContext();
            mp4VideoUri = Uri.parse(videoURL);

            initializePlayer(mp4VideoUri);
            /*player =
                    ExoPlayerFactory.newSimpleInstance(context, trackSelector, loadControl);

            simpleExoPlayerView.setPlayer(player);
            // Measures bandwidth during playback. Can be null if not required.
// Produces DataSource instances through which media data is loaded.
            DataSource.Factory dataSourceFactory = new DefaultDataSourceFactory(context, Util.getUserAgent(context, getTag()));
// Produces Extractor instances for parsing the media data.
            ExtractorsFactory extractorsFactory = new DefaultExtractorsFactory();
// This is the MediaSource representing the media to be played.
            videoSource = new ExtractorMediaSource(mp4VideoUri,
                    dataSourceFactory, extractorsFactory, null, null);
// Prepare the player with the source.

            player.prepare(videoSource);
            player.setPlayWhenReady(true);*/
            descTextView.setText(desc);
            fullscreen.setVisibility(View.VISIBLE);

        } else {
            noVideo.setVisibility(View.VISIBLE);
            if (TextUtils.equals(imageURL,"") ){
                imageView.setVisibility(View.VISIBLE);

                Picasso.with(getContext()).load(imageURL).into(imageView);
            }
            fullscreen.setVisibility(View.GONE);
            simpleExoPlayerView.setVisibility(View.GONE);
            descTextView.setText(desc);
        }
        if (getActivity().findViewById(R.id.step_tab_layout) == null) {
            fullscreen.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(getContext(), FullscreenActivity.class);
                    position = player.getCurrentPosition();
                    intent.putExtra("url", videoURL);
                    intent.putExtra("position", position);
                    startActivityForResult(intent, 2);
                }
            });
        } else fullscreen.setVisibility(View.GONE);

        return rootview;
    }

    private void initializePlayer(Uri mediaUri) {
        if (player == null) {
            TrackSelector trackSelector = new DefaultTrackSelector();

            DataSource.Factory dataSourceFactory = new DefaultDataSourceFactory(getContext(), Util.getUserAgent(getContext(), getTag()));
            LoadControl loadControl = new DefaultLoadControl();
            player = ExoPlayerFactory.newSimpleInstance(getContext(), trackSelector, loadControl);
            simpleExoPlayerView.setPlayer(player);
            String userAgent = Util.getUserAgent(getContext(), "Baking App");
            MediaSource mediaSource = new ExtractorMediaSource(mediaUri, new DefaultDataSourceFactory(getContext(), userAgent), new DefaultExtractorsFactory(), null, null);
            if (position != C.TIME_UNSET) player.seekTo(position);
            player.prepare(mediaSource);
            player.setPlayWhenReady(true);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // check if the request code is same as what is passed  here it is 2
        if (requestCode == 2) {
            if (mp4VideoUri != null) {
                position = data.getLongExtra("position", C.TIME_UNSET);
                initializePlayer(mp4VideoUri);
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (mp4VideoUri != null)
            initializePlayer(mp4VideoUri);
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        if (player != null) {
            player.stop();
            player.release();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (player != null) {
            position = player.getCurrentPosition();
            player.stop();
            player.release();
            player = null;
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putLong("position", position);

        super.onSaveInstanceState(outState);
    }
}
