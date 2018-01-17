import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;

/**
 * Created by Administrator on 2018/1/17.
 */
public class Utils {

    public static byte[] readImageFile(String path) {
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
