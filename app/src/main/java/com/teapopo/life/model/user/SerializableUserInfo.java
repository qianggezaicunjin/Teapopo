package com.teapopo.life.model.user;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

/**
 * Created by louiszgm on 2016/6/6.
 */
public class SerializableUserInfo implements Serializable {

    private transient UserInfo userinfo_in;//保存传入的对象
    private transient UserInfo userinfo_out;//反序列化后的对象

    public SerializableUserInfo(UserInfo userInfo) {
        this.userinfo_in = userInfo;
    }

    public UserInfo getUserinfo_out() {
        UserInfo bestUserInfo = userinfo_in;
        if (userinfo_out != null) {
            bestUserInfo = userinfo_out;
        }
        return bestUserInfo;
    }

    private void writeObject(ObjectOutputStream out) throws IOException {
        out.writeObject(userinfo_in.avatar);
        out.writeObject(userinfo_in.city);
        out.writeObject(userinfo_in.fans_num);
        out.writeObject(userinfo_in.id);
        out.writeObject(userinfo_in.nickname);
        out.writeObject(userinfo_in.points);
        out.writeObject(userinfo_in.posts_num);
        out.writeObject(userinfo_in.province);
        out.writeObject(userinfo_in.sex);
        out.writeObject(userinfo_in.signature);
        out.writeObject(userinfo_in.subscribe);
        out.writeObject(userinfo_in.subscribe_num);
        out.writeObject(userinfo_in.title);
    }

    private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
        String avatar = (String) in.readObject();
        String city = (String) in.readObject();
        String fans_num = (String) in.readObject();
        String id = (String) in.readObject();
        String nickname = (String) in.readObject();
        String points = (String) in.readObject();
        String posts_num = (String) in.readObject();
        String province = (String) in.readObject();
        String sex = (String) in.readObject();
        String signature = (String) in.readObject();
        String subscribe = (String) in.readObject();
        String subscribe_num = (String) in.readObject();
        String title = (String) in.readObject();

        userinfo_out = new UserInfo();
        userinfo_out.avatar = avatar;
        userinfo_out.city = city;
        userinfo_out.fans_num = fans_num;
        userinfo_out.id = id;
        userinfo_out.nickname = nickname;
        userinfo_out.points = points;
        userinfo_out.posts_num = posts_num;
        userinfo_out.province = province;
        userinfo_out.sex = sex;
        userinfo_out.signature = signature;
        userinfo_out.subscribe = subscribe;
        userinfo_out.subscribe_num = subscribe_num;
        userinfo_out.title = title;
    }
}
