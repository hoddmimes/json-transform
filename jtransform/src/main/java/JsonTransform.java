import com.hoddmimes.jtransform.Transform;

public class JsonTransform {

    public static void main( String[] args )
    {

        if (isStartedFromJar()) {
            if ((args.length != 2) || (args[0].compareToIgnoreCase("-xml") != 0)) {
                System.out.println("usage: JsonTransform -xml \"<message-definition-source-file>.xml\"");
                System.out.println("     args: " + args.length);
                for (int i = 0; i < args.length; i++) {
                    System.out.println("       arg: " + i + " value: " + args[i]);
                }
                System.exit(0);
            }
        }

        Transform tTransform = new Transform();
        tTransform.parseParameters( args  );
        tTransform.transform();
    }

    private static boolean isStartedFromJar()
    {
       String tResource =  JsonTransform.class.getResource("JsonTransform.class").toString();
       return tResource.startsWith("jar");
    }
}
