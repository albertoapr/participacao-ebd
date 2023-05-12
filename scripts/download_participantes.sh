# URL do arquivo do Google Drive
file_url="https://docs.google.com/spreadsheets/d/1zCY9DtKMQPF5s_YHXaM0TGFi8YKNdZdQja_22GnUeZQ/export?format=csv&id=1zCY9DtKMQPF5s_YHXaM0TGFi8YKNdZdQja_22GnUeZQ&gid=532508908"

# Nome do arquivo de destino
destination_file="participantes.csv"

# Baixar o arquivo usando o curl
curl -L -o "$destination_file" "$file_url"



echo "Download de participantes concluído no arquivo '$destination_file'."
