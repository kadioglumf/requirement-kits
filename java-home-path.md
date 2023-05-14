### MacOs Monterey için Java ve Maven Home ayarlama

`/usr/libexec/java_home -V`  ile yüklü tüm versiyonlar görüntülenir.

`open ~/.zshrc` ya da `open ~/.bash_profile` (sanırım mac'in eski sürümleri için geçerli.)  ile açılan dosyaya aşağıdaki veriler girilir


```
export JAVA_HOME=$(/usr/libexec/java_home -v 11.0.18)
#export JAVA_HOME=$(/usr/libexec/java_home -v 15.0.2)
export PATH=/opt/homebrew/bin:/usr/local/bin:/usr/bin:/bin:/usr/sbin:/sbin:/Library/Apple/usr/bin
export PATH=/opt/homebrew/bin:/opt/homebrew/bin:/usr/local/bin:/usr/bin:/bin:/usr/sbin:/sbin:/Library/Apple/usr/bin

export M2_HOME="/Users/kadioglumf/Downloads/apache-maven-3.8.1"
PATH="${M2_HOME}/bin:${PATH}"
export PATH
```


