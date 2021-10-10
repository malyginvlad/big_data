1. Создайте папку в корневой HDFS-папке
```
hdfs dfs -mkdir /new_folder
```
2. Создайте в созданной папке новую вложенную папку.
```
hdfs dfs -mkdir /new_folder/block_2
```
3. Что такое Trash в распределенной FS? Как сделать так, чтобы файлы удалялись сразу, минуя “Trash”?
```
hdfs dfs -rm -skipTrash
```
4. Создайте пустой файл в подпапке из пункта 2.
```
hdfs dfs -touchz /new_folder/block_2/new_file.txt
```
5. Удалите созданный файл.
```
hdfs dfs -rm -r /new_folder/block_2/new_file.txt
```
6. Удалите созданные папки.
```
hdfs dfs -rm -r /new_folder/block_2
```

1. Скопируйте любой в новую папку на HDFS
```
hdfs fs -cp /new_folder/block_2/new_file.txt /new_folder/new_block_2
```
2. Выведите содержимое HDFS-файла на экран.
```
hdfs fs -cat /new_folder/block_2/new_file.txt
```
3. Выведите содержимое нескольких последних строчек HDFS-файла на экран.
```
hdfs fs -tail /new_folder/block_2/new_file.txt
```
4. Выведите содержимое нескольких первых строчек HDFS-файла на экран.
```
hdfs fs -head /new_folder/block_2/new_file.txt
```
5. Переместите копию файла в HDFS на новую локацию.
```
hdfs fs -put /new_folder/block_2/new_file.txt /new_folder/new_block_2
```

2. Изменить replication factor для файла. Как долго занимает время на увеличение /
уменьшение числа реплик для файла?
```
hdfs dfs -setrep -w 4 /new_folder/block_2/new_file.txt
```
Отработало за секунд 5-10 для увеличения и также для уменьшения для пустого файла.

3. Найдите информацию по файлу, блокам и их расположениям с помощью “hdfs fsck”
```
hdfs fsck /new_folder/block_2/new_file.txt -files -blocks -locations
```
4. Получите информацию по любому блоку из п.2 с помощью "hdfs fsck -blockId”.
```
hdfs fsck -blockId blk_1073741825
```