package com.vr.ashley.fragments;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.speech.tts.TextToSpeech;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import com.vr.ashley.Managers.ChatHistoryManager;
import com.vr.ashley.Managers.PrefManager;
import com.vr.ashley.R;
import com.vr.ashley.activities.MainActivity;
import com.vr.ashley.utils.Utils;
import com.vr.ashley.adapter.ChatAdapter;
import com.vr.ashley.domain.ChatHistory;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.Random;

import ai.api.AIServiceException;
import ai.api.android.AIConfiguration;
import ai.api.AIDataService;
import ai.api.model.AIRequest;
import ai.api.model.AIResponse;

/**
 * Class for About the application
 *
 * @author Ashish Katre
 */
public class SpeechFragment extends Fragment implements View.OnClickListener {

    private Random random;
    public static List<ChatHistory> chatHistoryList;
    public static ChatAdapter chatAdapter;
    private ListView listViewSpeech;
    public static final String ACCESS_TOKEN = "a530e9aa0a6e4bf3a3050820005f754b";
    private TextToSpeech textToSpeech;
    public static final int REQUEST_CODE = 12;
    private final int REQ_CODE_SPEECH_INPUT = 100;
    private PrefManager prefManager;
    private Context context;
    public static final String LOG = "SpeechFragment";

    public static String result;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_speech, container, false);
        random = new Random();

        context = getActivity().getApplicationContext();

        result = null;

        listViewSpeech = (ListView) view.findViewById(R.id.listViewSpeech);

        ImageButton sendButton = (ImageButton) view
                .findViewById(R.id.imageButtonSpeech);
        sendButton.setOnClickListener(this);

        textToSpeech = new TextToSpeech(context, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (status != TextToSpeech.ERROR) {
                    textToSpeech.setLanguage(Locale.UK);
                }
            }
        });

        // ----Set autoscroll of listview when a new message arrives----//
        listViewSpeech.setTranscriptMode(ListView.TRANSCRIPT_MODE_ALWAYS_SCROLL);
        listViewSpeech.setStackFromBottom(true);

        prefManager = new PrefManager(context);

        ChatHistoryManager chatHistoryManage = new ChatHistoryManager();

        Log.d(LOG, "patient " + prefManager.getPatientId() + "and doctor id : " + prefManager.getDoctorId());

        chatHistoryList = chatHistoryManage.findAll(context, prefManager.getPatientId(), prefManager.getDoctorId());

        chatAdapter = new ChatAdapter(getActivity(), chatHistoryList);

        listViewSpeech.setAdapter(chatAdapter);

        return view;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.imageButtonSpeech:

                Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
                intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, "en-US");

//                Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
//                intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
//                        RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
//                intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
//                intent.putExtra(RecognizerIntent.EXTRA_PROMPT,
//                        getString(R.string.speech_prompt));
                try {

                    startActivityForResult(intent, REQ_CODE_SPEECH_INPUT);

                    if(SpeechFragment.result != null){

                        Toast.makeText(getActivity().getApplicationContext(),SpeechFragment.result,
                                Toast.LENGTH_SHORT).show();
                    }

                } catch (ActivityNotFoundException a) {

                    a.printStackTrace();

                    Toast.makeText(getActivity().getApplicationContext(),
                            getString(R.string.speech_not_supported),
                            Toast.LENGTH_SHORT).show();
                }

                break;
        }
    }

    /**
     * Receiving speech input
     */
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQ_CODE_SPEECH_INPUT) {

            if (resultCode == getActivity().RESULT_OK && null != data) {

                ArrayList<String> result = data
                        .getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);

                addMessage(result.get(0), false);

                getAIResponse(result.get(0));

                SpeechFragment.result = result.get(0);

                navigateToMainActivity();
            }
        }
    }

    private void addMessage(String message, boolean isRight) {

        ChatHistory chatHistory = null;

        if (isRight) {

            chatHistory = new ChatHistory(Utils.getCurrentDate(), prefManager.getPatientId(), prefManager.getDoctorId(), 2, message);

        } else {

            chatHistory = new ChatHistory(Utils.getCurrentDate(), prefManager.getPatientId(), prefManager.getDoctorId(), 1, message);
        }

        ChatHistoryManager chatHistoryManager = new ChatHistoryManager(chatHistory);
        chatHistoryManager.insert(context);

        if (message.contains("https://www.youtube.com/")) {

            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(message.substring(12))));

        }else if(isRight){

            textToSpeech.speak(message,TextToSpeech.QUEUE_FLUSH, null);
        }

        chatAdapter.add(chatHistory);
        chatAdapter.notifyDataSetChanged();
    }


    private void getAIResponse(String requestText) {

        final AIConfiguration config = new AIConfiguration(ACCESS_TOKEN,
                AIConfiguration.SupportedLanguages.English,
                AIConfiguration.RecognitionEngine.System);

        final AIDataService aiDataService = new AIDataService(config);

        final AIRequest aiRequest = new AIRequest();
        aiRequest.setQuery(requestText);

        new AsyncTask<AIRequest, Void, AIResponse>() {

            @Override
            protected AIResponse doInBackground(AIRequest... requests) {

                final AIRequest request = requests[0];

                try {

                    final AIResponse response = aiDataService.request(aiRequest);
                    return response;

                } catch (AIServiceException e) {


                }

                return null;
            }

            @Override
            protected void onPostExecute(AIResponse aiResponse) {

                if (aiResponse != null) {

                    String reply = aiResponse.getResult().getFulfillment().getSpeech();

                    addMessage(reply, true);

                    //navigateToMainActivity();
                }
            }
        }.execute(aiRequest);
    }

    /**
     * Navigation to main activity
     */
    private void navigateToMainActivity() {

        Intent intent = new Intent(context, MainActivity.class);
        MainActivity.currentFragment = SpeechFragment.class.getName();
        this.getActivity().startActivity(intent);

//        Fragment currentFragment = this;
//        FragmentTransaction fragTransaction = getFragmentManager().beginTransaction();
//        fragTransaction.detach(currentFragment);
//        fragTransaction.attach(currentFragment);
//        fragTransaction.commit();
    }
}