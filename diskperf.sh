echo test starts on `date +"%F %T"`
dd if=/dev/zero of=/tmp/test1.img bs=10G count=1 oflag=dsync
echo test starts on `date +"%F %T"`
dd if=/dev/zero of=/tmp/test2.img bs=512 count=1000 oflag=dsync
echo test starts on `date +"%F %T"`
