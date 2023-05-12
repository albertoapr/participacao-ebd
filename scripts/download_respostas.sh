# URL do arquivo do Google Drive
file_url="https://docs.google.com/document/export?format=txt&id=1c2CyPDuJB87XmnTchLR2PFqfSjqNzCy32jozXYfu7O8"

# Nome do arquivo de destino
destination_file="respostas.doc"

# Baixar o arquivo usando o curl
curl -L -o "$destination_file" "$file_url"

# Converter o arquivo DOC para TXT usando o textutil
textutil -convert txt "$destination_file"

# Renomear o arquivo TXT
txt_file="${destination_file%.*}.txt"
mv "$destination_file" "$txt_file"

echo "Conversão concluída. O arquivo TXT foi salvo como '$txt_file'."
