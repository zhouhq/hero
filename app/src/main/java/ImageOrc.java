import com.baidu.aip.ocr.AipOcr;
import org.json.JSONArray;
import org.json.JSONObject;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;

public class ImageOrc {
    public static void main(String arg[]) {
        new ImageOrc().ocrImage();
    }

    AipOcr aipOcr;
    public static final String APP_ID = "10703133";
    public static final String API_KEY = "b7NfAl9GS9qp4e03sqlyiS6U";
    public static final String SECRET_KEY = "UHOfSyRPyUTIzGfcaUcgWrKrzmwr5B1R";

    public ImageOrc() {
        init();
    }

    public void init() {
        aipOcr = new AipOcr(APP_ID, API_KEY, SECRET_KEY);
        aipOcr.setConnectionTimeoutInMillis(2000);
        aipOcr.setSocketTimeoutInMillis(60000);
    }

    public String[] ocrImage(byte[] data) {
        HashMap<String, String> options = new HashMap<String, String>();
        options.put("language_type", "CHN_ENG");
        options.put("detect_direction", "true");
        options.put("detect_language", "true");
        options.put("probability", "true");
        JSONObject res = aipOcr.basicGeneral(data, options);
        String words[]=parseResult(res);
        System.err.println("words"+words);
        return words;
    }

    public String[] ocrImage() {
        HashMap<String, String> options = new HashMap<String, String>();
        options.put("language_type", "CHN_ENG");
        options.put("detect_direction", "true");
        options.put("detect_language", "true");
        options.put("probability", "true");
        // 参数为本地图片二进制数组

        String image = "d:\\test.png";
        byte[] file = readImageFile(image);
        JSONObject res = aipOcr.basicGeneral(file, options);

        String words[]= parseResult(res);
        System.err.println("words"+words);
        return words;
    }

    /**
     * words_result_num 	是 	number 	识别结果数，表示words_result的元素个数
     * words_result 	是 	array 	定位和识别结果数组
     * +words 	否 	string 	识别结果字符串
     * probability 	否 	object 	行置信度信息；如果输入参数 probability = true 则输出
     * +average 	否 	number 	行置信度平均值
     * +variance 	否 	number 	行置信度方差
     * +min
     */
    private String[] parseResult(JSONObject result) {
        int num = result.optInt("words_result_num");
        JSONArray resultArray = result.getJSONArray("words_result");
        String words[] = new String[resultArray.length()];
        for (int i = 0; i < resultArray.length(); i++) {
            JSONObject item = (JSONObject) resultArray.get(i);
            words[i] = item.optString("words");
        }
        return words;
    }

    public byte[] readImageFile(String path) {
        File f = new File(path);
        try {
            BufferedImage image = ImageIO.read(f);
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            boolean flag = ImageIO.write(image, "jpg", out);
            byte[] b = out.toByteArray();
            return b;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
