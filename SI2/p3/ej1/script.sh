ssh-keygen -t dsa
cp .ssh/id_dsa.pub .ssh/authorized_keys2
scp .ssh/authorized_keys2 si2@10.5.7.2:/home/si2/.ssh
scp .ssh/authorized_keys2 si2@10.5.7.3:/home/si2/.ssh
