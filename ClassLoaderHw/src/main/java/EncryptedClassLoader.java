import java.io.*;

public class EncryptedClassLoader extends ClassLoader {
    private final String key;
    private final File dir;

    public EncryptedClassLoader(String key, File dir, ClassLoader parent) {
        super(parent);
        this.key = key;
        this.dir = dir;
    }

    @Override
    public Class<?> findClass(String name) {
        byte[] b = loadData(name);
        b = encryptData(b);
        return defineClass(name, b, 0, b.length);
    }

    private byte[] encryptData(byte[] raw_data) {
        for (int i = 0; i < raw_data.length; ++i) {
            raw_data[i] += Integer.parseInt(key);
        }
        return raw_data;
    }

    private byte[] loadData(String classname)  {
        String fullpath = dir.getPath() + "/" + classname.replace('.', '/') + ".class";
        InputStream inputStream = null;
        try {
            inputStream = new FileInputStream(fullpath);
        } catch (FileNotFoundException e) {
            System.out.println("Class not found");
            e.printStackTrace();
        }
        byte[] buffer;
        ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
        int nextValue = 0;
        try {
            while ( (nextValue = inputStream.read()) != -1 ) {
                byteStream.write(nextValue);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        buffer = byteStream.toByteArray();
        return buffer;
    }
}
