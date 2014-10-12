package com.example.WinnieProject.grouplist;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.*;
import com.example.WinnieProject.R;
import com.example.WinnieProject.RequestToDB;
import com.example.WinnieProject.User;
import com.example.WinnieProject.UserInfo;
import com.example.WinnieProject.adapters.UserAdapter;

import java.util.ArrayList;

/**
 * Created by Юрий on 11.10.2014.
 */
public class Favorites extends Activity {
    private ArrayList<User> users = new ArrayList<User>();
    private ListView listView_friends;
    private ArrayAdapter<User> arrayAdapter;


    private String getBalance() {

        return "0.00Руб";
    }


    private void refreshBalance() {
        TextView balance = (TextView) findViewById(R.id.MyBalanceListFavorites);
        balance.setText(getBalance());
    }

    private ArrayList<User> getTrueUsers(ArrayList<User> list, String sub) {
        ArrayList<User> ans = new ArrayList<User>();
        for (User user : list) {
            if (user.name.toUpperCase().contains(sub.toUpperCase()) || user.yandexMoneyNumber.toUpperCase().contains(sub.toUpperCase()) || user.phoneNumber.toUpperCase().contains(sub.toUpperCase())) {
                ans.add(user);
            }
        }
        return ans;
    }

    private ArrayList<User> getFavoriteUsers() {
        ArrayList<User> userArrayList = new ArrayList<User>();
        //find friends in DB

        userArrayList.add(new User("Вася", User.noFilled, "4100014255715", "+79012345678", 0, true));
        userArrayList.add(new User("Петя", "vk.com/id123", "4100014255715", "+79012345678", 0, true));
        userArrayList.add(new User("Леха", "vk.com/id123", "4100014255715", "+79012345678", 0, true));
        return userArrayList;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_favorites_friends);
        refreshBalance();
        showFriends();
        listView_friends.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent();
                intent.putExtra(RequestToDB.PHONE_NUMBER, users.get(i).phoneNumber);
                intent.putExtra(RequestToDB.NAME, users.get(i).name);
                intent.putExtra(RequestToDB.VK_ID, users.get(i).vkId);
                intent.putExtra(RequestToDB.YANDEX_MONEY_NUMBER, users.get(i).yandexMoneyNumber);
                intent.putExtra(RequestToDB.PICTURE_ID, users.get(i).pictureId);
                intent.putExtra(RequestToDB.IS_FAVORITE, users.get(i).isFavorite);
                intent.setClass(getApplicationContext(), UserInfo.class);
                startActivity(intent);
            }
        });

        EditText editText = (EditText) findViewById(R.id.findFavoriteFriendWithSub);
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
                showFriends();
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
                showFriends();
            }

            @Override
            public void afterTextChanged(Editable editable) {
                showFriends();
            }
        });


    }

    private void showFriends() {


        users = getFavoriteUsers();
        EditText editText = (EditText) findViewById(R.id.findFavoriteFriendWithSub);
        ArrayList<User> trueUsers = getTrueUsers(users, editText.getText().toString());

        arrayAdapter = new UserAdapter(getApplicationContext(), trueUsers);
        listView_friends = (ListView) findViewById(R.id.listViewFavorites);
        listView_friends.setAdapter(arrayAdapter);

    }

    @Override
    public void onResume() {
        super.onResume();
        refreshBalance();
    }
}
