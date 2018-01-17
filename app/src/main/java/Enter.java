/**
 * Created by Administrator on 2018/1/16.
 */
public class Enter {
    CreateBitmap createBitmap;
    ImageOrc orc;
    public static void main(String arg[]) {
      new Enter().run();
        System.exit(0);
    }
    public Enter()
    {
        createBitmap = new CreateBitmap();
        orc = new ImageOrc();
    }
    public void run()
    {
        byte[] data=createBitmap.getImage();
       String result[]= orc.ocrImage(data);
        System.err.println(result);
    }
}
