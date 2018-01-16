import com.android.ddmlib.*;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.File;
import java.io.IOException;

/**
 * Created by Administrator on 2018/1/16.
 */
public class CreateBitmap {
    public static void main(String arg[]) {
        new CreateBitmap().getImage();
    }

    public byte[] getImage() {
        long t = System.currentTimeMillis();
        IDevice device;
        AndroidDebugBridge.init(false);
        AndroidDebugBridge bridge = AndroidDebugBridge.createBridge();

        watiDeviceList(bridge);
        IDevice devices[] = bridge.getDevices();
        //直接取第一个
        device = devices[0];
        try {
            long  t00= System.currentTimeMillis();
            RawImage rawImage = device.getScreenshot();
            long  t01= System.currentTimeMillis();
            System.err.println("getScreenshot time=" + (t01 - t00));
            if (rawImage != null) {
                saveImage(rawImage);
                return rawImage.data;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        long c = System.currentTimeMillis();
        System.err.println("getImage time=" + (c - t));
        return null;
    }

    /**
     * 等待设备的连接发
     */
    private static void watiDeviceList(AndroidDebugBridge bridge) {
        long t = System.currentTimeMillis();
        int count = 0;
        while (bridge.hasInitialDeviceList() == false) {
            try {
                Thread.sleep(100);
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (count > 30) {
                System.err.println("wait device time out");
                break;
            }
            count++;

        }
        long c = System.currentTimeMillis();
        System.err.println("watiDeviceList time=" + (c - t));
    }

    private static void saveImage(RawImage rawImage) {
        BufferedImage image = null;
        image = new BufferedImage(rawImage.width, rawImage.height, BufferedImage.TYPE_INT_ARGB);
        int index = 0;
        int indexInc = 4;
        long t1 = System.currentTimeMillis();
        for (int y = 0; y < rawImage.height; y++) {
            for (int x = 0; x < rawImage.width; x++, index += indexInc) {

                int value = rawImage.getARGB(index);
                image.setRGB(x, y, value);
            }
        }
        try {
            ImageIO.write((RenderedImage) image, "PNG", new File("D:/temp.jpg"));
            System.err.println("save image succeed");
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
