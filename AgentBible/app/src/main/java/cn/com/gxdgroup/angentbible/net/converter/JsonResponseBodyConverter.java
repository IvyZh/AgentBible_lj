package cn.com.gxdgroup.angentbible.net.converter;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;

import cn.com.gxdgroup.angentbible.utils.L;
import okhttp3.ResponseBody;
import retrofit2.Converter;

/**
 * Created by Ivy on 2016/10/31.
 *
 * @description:
 */

final class JsonResponseBodyConverter<T> implements Converter<ResponseBody, T> {
    private JSONObject jsonObject;

    JsonResponseBodyConverter(JSONObject jsonObject) {

    }

    @Override
    public T convert(ResponseBody value) throws IOException {
        BufferedReader br = new BufferedReader(value.charStream());
        String line = "";
        StringBuffer buffer = new StringBuffer();
        while ((line = br.readLine()) != null) {
            buffer.append(line);
        }
        //buffer.toString()
        L.v("接收数据:" + buffer.toString());
        try {
            jsonObject = new JSONObject(buffer.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return (T) jsonObject;
    }
}