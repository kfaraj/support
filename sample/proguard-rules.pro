-keepattributes SourceFile,LineNumberTable
-renamesourcefileattribute SourceFile

-keep public class * extends android.view.View {
    public <init>(...);
}

-keep public class * extends android.view.ActionProvider {
    public <init>(...);
}

-keep public class * extends android.support.v4.view.ActionProvider {
    public <init>(...);
}
