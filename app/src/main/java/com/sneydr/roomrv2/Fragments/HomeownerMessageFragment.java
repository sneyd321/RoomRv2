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
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.OnLifecycleEvent;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.github.nkzawa.emitter.Emitter;
import com.github.nkzawa.engineio.client.Transport;
import com.github.nkzawa.socketio.client.IO;
import com.github.nkzawa.socketio.client.Manager;
import com.github.nkzawa.socketio.client.Socket;
import com.google.gson.Gson;
import com.google.gson.JsonParser;
import com.sneydr.roomrv2.Adapters.MessageRecyclerViewAdapter;
import com.sneydr.roomrv2.App.TextInput.NormalTextInput.FirstNameTextInput;
import com.sneydr.roomrv2.App.TextInput.NormalTextInput.MessageTextInput;
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
import com.sneydr.roomrv2.SocketIO.Callbacks.CallbackType;
import com.sneydr.roomrv2.SocketIO.Observers.DisconnectObserver;
import com.sneydr.roomrv2.SocketIO.Observers.JoinObserver;
import com.sneydr.roomrv2.SocketIO.Observers.MessageObserver;
import com.sneydr.roomrv2.SocketIO.SocketIO;
import com.sneydr.roomrv2.ViewModels.HomeownerViewModel;
import com.sneydr.roomrv2.ViewModels.TenantViewModel;

import org.json.JSONObject;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Request;

public class HomeownerMessageFragment extends FragmentTemplate implements HomeownerObserver, JoinObserver, MessageObserver, DisconnectObserver {


    private MessageRecyclerViewAdapter adapter;
    private RecyclerView rcyMessages;
    private MessageTextInput message;
    private ImageButton btnSend;

    private int houseId;
    private String authToken;

    private MessageFactory factory;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_message, container, false);
        rcyMessages = view.findViewById(R.id.rcyMessages);
        rcyMessages.setLayoutManager(new LinearLayoutManager(context));
        message = new MessageTextInput(view, R.id.tilMessage, R.id.edtMessage);
        message.resetError();
        btnSend = view.findViewById(R.id.btnSend);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        SocketIO socketIO = SocketIO.getInstance();
        socketIO.registerSocket("join", socketIO.getListener(CallbackType.onJoin, this));
        socketIO.registerSocket("message", socketIO.getListener(CallbackType.onMessage, this));
        socketIO.registerSocket("leave", socketIO.getListener(CallbackType.onDisconnect, this));
        socketIO.connect();
        message.resetError();
        HomeownerViewModel homeownerViewModel = ViewModelProviders.of(this).get(HomeownerViewModel.class);
        homeownerViewModel.loadHomeowner(authToken, this);
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    @Override
    public void onHomeowner(Homeowner homeowner) {
        handler.post(new Runnable() {
            @Override
            public void run() {
                adapter = new MessageRecyclerViewAdapter(getContext(), new ArrayList<>(), "Homeowner", homeowner.getEmail());
                rcyMessages.setAdapter(adapter);
                factory = new MessageFactory(homeowner.getEmail(), homeowner.getFullName(), "Homeowner", houseId);
                btnSend.setOnClickListener(onSend);
                Message joinMessage = factory.getMessage("Join Room");
                SocketIO socketIO = SocketIO.getInstance();
                socketIO.emitMessage("join", joinMessage);
            }
        });

    }

    View.OnClickListener onSend = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            SocketIO socketIO = SocketIO.getInstance();
            message.invokeValidation();
            if (message.getError() != null) {
                YoYo.with(Techniques.Shake).playOn(message.getEditText());
            }
            else {
                socketIO.emitMessage("message", factory.getMessage(message.getText()));
                message.getEditText().setText("");
                message.resetError();
            }

        }
    };


    public HomeownerMessageFragment setHouseId(int houseId) {
        this.houseId = houseId;
        return this;
    }

    public HomeownerMessageFragment setHomeownerId(String authToken) {
        this.authToken = authToken;
        return this;
    }


    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    @Override
    public void onPause() {
        super.onPause();
        if (factory != null){
            SocketIO socketIO = SocketIO.getInstance();
            socketIO.emitMessage("leave", factory.getMessage("Leave room"));
        }
    }

    @Override
    public void onMessage(Message message) {
        handler.post(new Runnable() {
            @Override
            public void run() {
                if (adapter != null) {
                    adapter.add(message);
                    rcyMessages.scrollToPosition(adapter.getItemCount() - 1);
                }
            }
        });
    }

    @Override
    public void onJoin(List<Message> messages) {
        handler.post(new Runnable() {
            @Override
            public void run() {
                if (adapter != null) {
                    adapter.refresh(messages);
                    rcyMessages.scrollToPosition(adapter.getItemCount() - 1);
                }
            }
        });
    }

    @Override
    public void onDisconnect(Message message) {
        SocketIO socketIO = SocketIO.getInstance();
        socketIO.disconnect();
        socketIO.clearSocket("join");
        socketIO.clearSocket("message");
        socketIO.clearSocket("leave");

    }
}
