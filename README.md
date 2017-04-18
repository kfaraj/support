Support Library
===============

This library is built on top of the [Android Support Library](https://developer.android.com/topic/libraries/support-library/index.html).

appcompat library
-----------------

This library is built on top of the [v7 appcompat library](https://developer.android.com/topic/libraries/support-library/packages.html#v7-appcompat) and is designed to be used with Android 3.0 (API level 11) and higher.

It adds support for image drawable tint to the following classes:
- [SupportImageButton](appcompat/src/main/java/com/kfaraj/support/widget/SupportImageButton.java)
- [SupportImageView](appcompat/src/main/java/com/kfaraj/support/widget/SupportImageView.java)

```java
SupportImageView imageView = new SupportImageView(context);
imageView.setSupportImageTintList(tint);
imageView.setSupportImageTintMode(tintMode);
```

```xml
<com.kfaraj.support.widget.SupportImageView
    android:layout_width="wrap_content"
    android:layout_height="wrap_content"
    app:tint="@color/tint"
    app:tintMode="multiply" />
```

It adds support for compound drawables tint to the following classes:
- [SupportAutoCompleteTextView](appcompat/src/main/java/com/kfaraj/support/widget/SupportAutoCompleteTextView.java)
- [SupportButton](appcompat/src/main/java/com/kfaraj/support/widget/SupportButton.java)
- [SupportCheckBox](appcompat/src/main/java/com/kfaraj/support/widget/SupportCheckBox.java)
- [SupportCheckedTextView](appcompat/src/main/java/com/kfaraj/support/widget/SupportCheckedTextView.java)
- [SupportEditText](appcompat/src/main/java/com/kfaraj/support/widget/SupportEditText.java)
- [SupportMultiAutoCompleteTextView](appcompat/src/main/java/com/kfaraj/support/widget/SupportMultiAutoCompleteTextView.java)
- [SupportRadioButton](appcompat/src/main/java/com/kfaraj/support/widget/SupportRadioButton.java)
- [SupportTextView](appcompat/src/main/java/com/kfaraj/support/widget/SupportTextView.java)

```java
SupportTextView textView = new SupportTextView(context);
textView.setSupportCompoundDrawableTintList(tint);
textView.setSupportCompoundDrawableTintMode(tintMode);
```

```xml
<com.kfaraj.support.widget.SupportTextView
    android:layout_width="wrap_content"
    android:layout_height="wrap_content"
    app:drawableTint="@color/tint"
    app:drawableTintMode="multiply" />
```

This library can be included with the following dependency:
```groovy
compile 'com.kfaraj.support:appcompat:1.1.1'
```

recyclerview library
--------------------

This library is built on top of the [v7 recyclerview library](https://developer.android.com/topic/libraries/support-library/packages.html#v7-recyclerview) and is designed to be used with Android 3.0 (API level 11) and higher.

It adds support for empty view, item click and choice mode to the following class:
- [SupportRecyclerView](recyclerview/src/main/java/com/kfaraj/support/widget/SupportRecyclerView.java)

```java
SupportRecyclerView recyclerView = new SupportRecyclerView(context);
recyclerView.setEmptyView(emptyView);
recyclerView.setOnItemClickListener(onItemClickListener);
recyclerView.setOnItemLongClickListener(onItemLongClickListener);
recyclerView.setChoiceMode(choiceMode);
recyclerView.setMultiChoiceModeListener(multiChoiceModeListener);
```

```xml
<com.kfaraj.support.widget.SupportRecyclerView
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    app:choiceMode="singleChoice" />
```

This library can be included with the following dependency:
```groovy
compile 'com.kfaraj.support:recyclerview:1.1.1'
```

sample application
------------------

This application demonstrates the features of the Support Library.
