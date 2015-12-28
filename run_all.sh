set -e

dj() {
  docker run --rm -it -v /var/run/docker.sock:/var/run/docker.sock -v "$PWD":/app -v "$HOME":/root -w /app devo/dj $@
}

echo "ruby"
cd ruby
dj -e "PAYLOAD_FILE=hello.payload.json" -e "YOUR_ENV_VAR=ANYTHING" ruby run hello.rb
cd ..

echo "go"
cd go
dj go build
dj -e "PAYLOAD_FILE=hello.payload.json" -e "YOUR_ENV_VAR=ANYTHING" go run
cd ..

echo "node"
cd node
dj -e "PAYLOAD_FILE=hello.payload.json" -e "YOUR_ENV_VAR=ANYTHING" node run hello.js
cd ..

echo "php"
cd php
dj -e "PAYLOAD_FILE=hello.payload.json" -e "YOUR_ENV_VAR=ANYTHING" php run hello.php
cd ..

echo "python"
cd python
dj -e "PAYLOAD_FILE=hello.payload.json" -e "YOUR_ENV_VAR=ANYTHING" python run hello.py
cd ..
