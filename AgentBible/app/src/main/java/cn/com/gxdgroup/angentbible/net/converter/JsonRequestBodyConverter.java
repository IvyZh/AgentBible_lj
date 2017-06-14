package cn.com.gxdgroup.angentbible.net.converter;

import org.json.JSONObject;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.nio.charset.Charset;

import cn.com.gxdgroup.angentbible.utils.L;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import okio.Buffer;
import retrofit2.Converter;

/**
 * Created by Ivy on 2016/10/31.
 *
 * @description:
 */

final class JsonRequestBodyConverter<T> implements Converter<T, RequestBody> {
    private static final MediaType MEDIA_TYPE = MediaType.parse("application/json; charset=UTF-8");
    private static final Charset UTF_8 = Charset.forName("UTF-8");
    private JSONObject jsonObject;

    JsonRequestBodyConverter(JSONObject jsonObject) {

        this.jsonObject = jsonObject;
    }

    @Override
    public RequestBody convert(T value) throws IOException {
        Buffer buffer = new Buffer();
        Writer writer = new OutputStreamWriter(buffer.outputStream(), UTF_8);
        L.v("提交数据:" + value.toString());
        writer.write(value.toString());
        writer.close();
        return RequestBody.create(MEDIA_TYPE, buffer.readByteString());
    }
}