-renamesourcefileattribute SourceFile
-keepattributes SourceFile,LineNumberTable

-keep public class * extends android.app.Activity
-keep public class * extends android.app.Application
-keep public class * extends android.app.Fragment
-keep public class * extends android.app.Service
-keep public class * extends android.app.backup.BackupAgent
-keep public class * extends android.content.BroadcastReceiver
-keep public class * extends android.content.ContentProvider
-keep public class * extends android.view.ActionProvider {
    public <init>(...);
}
-keep public class * extends android.view.View {
    public <init>(...);
}

-keep public class * extends android.support.v4.app.Fragment
-keep public class * extends android.support.v7.widget.RecyclerView$LayoutManager {
    public <init>(...);
}
