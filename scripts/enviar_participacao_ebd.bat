@echo off

echo Atualizando arquivo de respostas
wget -O respostas.txt "https://docs.google.com/document/u/0/export?format=txt&id=1c2CyPDuJB87XmnTchLR2PFqfSjqNzCy32jozXYfu7O8"

echo Atualizando lista de participantes
wget -O participantes.csv "https://docs.google.com/spreadsheets/d/1zCY9DtKMQPF5s_YHXaM0TGFi8YKNdZdQja_22GnUeZQ/export?format=csv&id=1zCY9DtKMQPF5s_YHXaM0TGFi8YKNdZdQja_22GnUeZQ&gid=532508908"

setlocal

set "data=%date:~-4,4%%date:~-7,2%%date:~-10,2%"
set "arquivo_log=C:\Users\bpd\Dropbox\ParticipacaoEBD\LOG\log_%data%.txt"

findstr /C:"enviadas" "%arquivo_log%" >nul
if %errorlevel% neq 0 (
	echo Enviando as participações
        java -jar "C:\Users\bpd\Dropbox\ParticipacaoEBD\participacao-ebd.jar" -t 1> "C:\Users\bpd\Dropbox\ParticipacaoEBD\LOG\log_%date:~-4,4%%date:~-7,2%%date:~-10,2%.txt" 2>&1
    )else (
	echo Envio ja realizado anteriormente
)

echo Removendo logs antigos
powershell.exe "Get-Item -Path C:\Users\bpd\Dropbox\ParticipacaoEBD\LOG\* | Where-Object {$_.CreationTime -lt (Get-Date).AddDays(-30)} | Remove-Item -Recurse -Confirm:$false" 1> "C:\Users\bpd\Dropbox\ParticipacaoEBD\LOG\log_%date:~-4,4%%date:~-7,2%%date:~-10,2%.delete.txt" 2>&1

endlocal

pause
