package com.sneydr.roomrv2.Fragments;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.github.nkzawa.emitter.Emitter;
import com.github.nkzawa.engineio.client.Transport;
import com.github.nkzawa.socketio.client.IO;
import com.github.nkzawa.socketio.client.Manager;
import com.github.nkzawa.socketio.client.Socket;
import com.google.gson.Gson;
import com.google.gson.JsonParser;
import com.sneydr.roomrv2.Adapters.MessageRecyclerViewAdapter;
import com.sneydr.roomrv2.App.TextInput.NormalTextInput.FirstNameTextInput;
import com.sneydr.roomrv2.App.TextInput.TextInput;
import com.sneydr.roomrv2.Entities.Message.Message;
import com.sneydr.roomrv2.Entities.Message.MessageFactory;
import com.sneydr.roomrv2.Entities.Users.Homeowner;
import com.sneydr.roomrv2.Network.Callbacks.GetHomeownerCallback;
import com.sneydr.roomrv2.Network.Callbacks.NetworkCallbackType;
import com.sneydr.roomrv2.Network.JSONParser;
import com.sneydr.roomrv2.Network.Network;
import com.sneydr.roomrv2.Network.Observers.HomeownerObserver;
import com.sneydr.roomrv2.R;
import com.sneydr.roomrv2.Repositories.HomeownerRepository;

import org.json.JSONObject;

import java.net.URISyntaxException;
import java.util.ArrayList;

import okhttp3.Request;

public class HomeownerMessageFragment extends FragmentTemplate implements HomeownerObserver {


    private MessageRecyclerViewAdapter adapter;
    private RecyclerView rcyMessages;
    private TextInput message;
    private ImageButton btnSend;
    private Socket mSocket;
    private int houseId;
    private int homeownerId;
    private JSONParser jsonParser;
    private HomeownerRepository homeownerRepository;

    private MessageFactory factory;

    @Override
    protected void initUI(View view) {
        try {
            mSocket = IO.socket("http://192.168.0.115:8087");
        } catch (URISyntaxException e) {
            Toast.makeText(context, "Error in connection string", Toast.LENGTH_SHORT).show();
        }
        mSocket.on("join", onJoin);
        mSocket.on("message", onMessage);
        mSocket.connect();
        jsonParser = JSONParser.getInstance();
        rcyMessages = view.findViewById(R.id.rcyMessages);
        rcyMessages.setLayoutManager(new LinearLayoutManager(context));
        adapter = new MessageRecyclerViewAdapter(context, new ArrayList<>(), 1);
        rcyMessages.setAdapter(adapter);
        message = new FirstNameTextInput(view, R.id.tilMessage, R.id.edtMessage);
        btnSend = view.findViewById(R.id.btnSend);
        homeownerRepository = new HomeownerRepository(this);
        homeownerRepository.getHomeowner(homeownerId);
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_message, container, false);
        initUI(view);
        return view;
    }


    @Override
    public void onHomeowner(Homeowner homeowner) {
        factory = new MessageFactory(homeowner.getHomeownerId(), homeowner.getFullName(), houseId);
        btnSend.setOnClickListener(onSend);
        Message joinMessage = factory.getMessage("Join Room");
        mSocket.emit("join", jsonParser.messageToJson(joinMessage));
    }

    View.OnClickListener onSend = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Message newMessage = factory.getMessage(message.getText());
            mSocket.emit("message", jsonParser.messageToJson(newMessage));
        }
    };

    private Emitter.Listener onMessage = new Emitter.Listener() {
        @Override
        public void call(Object... args) {
            Handler handler = new Handler(Looper.getMainLooper());
            handler.post(new Runnable() {
                @Override
                public void run() {
                    String message = (String) args[0];
                    JSONParser jsonParser = JSONParser.getInstance();
                    Message returnedMessage = jsonParser.parseMessage(message);
                    adapter.add(returnedMessage);
                }
            });
        }
    };

    private Emitter.Listener onJoin = new Emitter.Listener() {
        @Override
        public void call(Object... args) {
            Handler handler = new Handler(Looper.getMainLooper());
            handler.post(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(context, "Join callback", Toast.LENGTH_SHORT).show();
                }
            });
        }
    };


    public HomeownerMessageFragment setHouseId(int houseId) {
        this.houseId = houseId;
        return this;
    }

    public HomeownerMessageFragment setHomeownerId(int homeownerId) {
        this.homeownerId = homeownerId;
        return this;
    }



}
