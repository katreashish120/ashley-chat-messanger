package com.vr.ashley.fragments;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import com.vr.ashley.Managers.ChatHistoryManager;
import com.vr.ashley.Managers.PrefManager;
import com.vr.ashley.R;
import com.vr.ashley.utils.RestServiceHelper;
import com.vr.ashley.utils.Utils;
import com.vr.ashley.adapter.ChatAdapter;
import com.vr.ashley.domain.ChatHistory;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.List;
import java.util.Locale;
import java.util.Random;

import ai.api.AIDataService;
import ai.api.AIServiceException;
import ai.api.android.AIConfiguration;
import ai.api.model.AIRequest;
import ai.api.model.AIResponse;
import cz.msebera.android.httpclient.HttpEntity;
import cz.msebera.android.httpclient.HttpResponse;
import cz.msebera.android.httpclient.StatusLine;
import cz.msebera.android.httpclient.client.HttpClient;
import cz.msebera.android.httpclient.client.methods.HttpGet;
import cz.msebera.android.httpclient.impl.client.DefaultHttpClient;

/**
 * Class for About the application
 *
 * @author Ashish Katre
 */
public class TextFragment extends Fragment implements View.OnClickListener {

    private EditText msg_editText;
    private Random random;
    public static ChatAdapter chatAdapter;
    private ListView msgListView;
    private List<ChatHistory> chatHistoryList;
    // TODO please add dialogflow token
    public static final String ACCESS_TOKEN = "<dialogflow tocken>";
    private TextToSpeech textToSpeech;
    public static final int REQUEST_CODE = 12;
    private final int REQ_CODE_SPEECH_INPUT = 100;
    private PrefManager prefManager;
    private Context context;
    public static final String LOG = "TextFragment";
    // TODO please add dialogflow token
    private static final String url = "<URL>";
    private static final int HTTP_STATUS_OK = 200;
    private static final String errorMessage = "Could not connect to ashley, Please try again later";
    private static byte[] buff = new byte[1024];

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_text, container, false);
        random = new Random();

        context = getActivity().getApplicationContext();

        msg_editText = (EditText) view.findViewById(R.id.messageEditText);
        msgListView = (ListView) view.findViewById(R.id.msgListView);

        ImageButton sendButton = (ImageButton) view
                .findViewById(R.id.sendMessageButton);
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
        msgListView.setTranscriptMode(ListView.TRANSCRIPT_MODE_ALWAYS_SCROLL);
        msgListView.setStackFromBottom(true);

        ChatHistoryManager chatHistoryManage = new ChatHistoryManager();

        prefManager = new PrefManager(context);

        Log.d(LOG, "patient " + prefManager.getPatientId() + "and doctor id : " + prefManager.getDoctorId());

        chatHistoryList = chatHistoryManage.findAll(context, prefManager.getPatientId(), prefManager.getDoctorId());

        chatAdapter = new ChatAdapter(getActivity(), chatHistoryList);

        msgListView.setAdapter(chatAdapter);

        //new RestServiceHelper().execute("good");

        // TODO incase rest implementation instead of hitting chatbot API directly

        /*new AsyncTask<String, Void, String>() {

            @Override
            public String doInBackground(String... req) {

                Log.d(LOG, "*** In RestServiceHelper");

                StringBuffer response = new StringBuffer();

                try {

                    HttpClient httpClient = new DefaultHttpClient();
                    HttpGet getRequest = new HttpGet(url + req[0]);

                    HttpResponse httpResponse = httpClient.execute(getRequest);
                    StatusLine statusLine = httpResponse.getStatusLine();

                    if (statusLine.getStatusCode() != HTTP_STATUS_OK) {

                        response.append(errorMessage);

                    } else {

                        HttpEntity httpEntity = httpResponse.getEntity();
                        InputStream inputStream = httpEntity.getContent();
                        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                        int readCount = 0;

                        while ((readCount = inputStream.read(buff)) != -1) {

                            outputStream.write(buff, 0, readCount);
                        }

                        response.append(outputStream.toString());
                    }

                    Log.d(LOG, "*** Done");

                } catch (Exception e) {

                    response.append(errorMessage);

                    e.printStackTrace();

                    Log.d(LOG, "*** In Error");
                }

                return response.toString();
            }

            @Override
            protected void onPostExecute(String response) {

                if (response != null) {

                    Log.d(LOG, "*** AI REST Response " + response);

                    //Toast.makeText(context, "AI REST Response " + response, Toast.LENGTH_SHORT);
                }
            }
        }.execute("play%20video%20one");*/


        return view;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.sendMessageButton:

                sendTextMessage(v);
        }
    }

    public void sendTextMessage(View v) {

        String message = msg_editText.getEditableText().toString();

        if (!message.equalsIgnoreCase("")) {

            addMessage(message, false);

            getAIResponse(message);
        }
    }

    private void addMessage(String message, boolean isRight) {

        msg_editText.setText("");
        msg_editText.setEnabled(isRight);


        ChatHistory chatHistory = null;

        if (isRight) {

            chatHistory = new ChatHistory(Utils.getCurrentDate(), prefManager.getPatientId(), prefManager.getDoctorId(), 2, message);

        } else {

            chatHistory = new ChatHistory(Utils.getCurrentDate(), prefManager.getPatientId(), prefManager.getDoctorId(), 1, message);
        }

        if (message.contains("https://www.youtube.com/")) {

            ChatHistoryManager chatHistoryManager = new ChatHistoryManager(chatHistory);
            chatHistoryManager.insert(context);

            chatAdapter.add(chatHistory);

            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(message.substring(12))));

        } else if (message.contains("Play video x1")) {

            // Play Video

            Intent LaunchIntent = getActivity().getPackageManager().getLaunchIntentForPackage("com.edu.nus.iss.mhvrchatbot");
            startActivity(LaunchIntent);

        } else if (isRight) {

            ChatHistoryManager chatHistoryManager = new ChatHistoryManager(chatHistory);
            chatHistoryManager.insert(context);

            chatAdapter.add(chatHistory);

            textToSpeech.speak(message, TextToSpeech.QUEUE_FLUSH, null);

        } else {

            ChatHistoryManager chatHistoryManager = new ChatHistoryManager(chatHistory);
            chatHistoryManager.insert(context);

            chatAdapter.add(chatHistory);
        }

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
                }
            }
        }.execute(aiRequest);
    }
}