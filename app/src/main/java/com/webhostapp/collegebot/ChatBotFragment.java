package com.webhostapp.collegebot;

import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.gson.JsonElement;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;

import ai.api.android.AIConfiguration;
import ai.api.android.AIService;
import ai.api.model.AIError;
import ai.api.model.AIResponse;
import ai.api.model.Result;
import ai.api.ui.AIDialog;

public class ChatBotFragment extends Fragment {

    private Button listenButton;
    private TextView resultTextView;
    private AIService aiService;
    private TextToSpeech textToSpeech;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        listenButton = getView().findViewById(R.id.listenButton);
        resultTextView = getView().findViewById(R.id.resultTextView);
        final AIConfiguration config = new AIConfiguration("f4f25acd5aa943938c51383748ff1f34",
                AIConfiguration.SupportedLanguages.English,
                AIConfiguration.RecognitionEngine.System);
        aiService = AIService.getService(Objects.requireNonNull(getContext()), config);
        textToSpeech=new TextToSpeech(getContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if(status != TextToSpeech.ERROR) {
                    textToSpeech.setLanguage(Locale.UK);
                }
            }
        });
        listenButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AIDialog aiDialog = new AIDialog(getContext(), config);
                aiDialog.setResultsListener(new AIDialog.AIDialogListener() {
                    @Override
                    public void onResult(AIResponse result) {
                        String speech = result.getResult().getFulfillment().getSpeech();
                        resultTextView.setText(speech);
                        textToSpeech.speak(speech,TextToSpeech.QUEUE_FLUSH,null);
                    }

                    @Override
                    public void onError(AIError error) {

                    }

                    @Override
                    public void onCancelled() {

                    }
                });
                aiDialog.showAndListen();
            }
        });
    }

    public ChatBotFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_chat_bot, container, false);
    }
}
