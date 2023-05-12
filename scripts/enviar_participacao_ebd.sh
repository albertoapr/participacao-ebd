source download_respostas.sh
source download_participantes.sh

data=$(date +%Y%m%d)
arquivo_log="log_${data}.txt"

if [ ! -f "$arquivo_log" ] || ! grep -q "enviadas" "$arquivo_log"; then   
 java -jar participacao-ebd.jar -t 1> LOG/log_$(date +%Y%m%d).txt 2>&1
fi

find "LOG/" -name "*.txt" -type f -mtime +30 -exec rm {} \;
