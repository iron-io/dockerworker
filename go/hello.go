package main

import (
	"fmt"
	"github.com/iron-io/iron_go/worker"
)

type Person struct {
	Name string
}

func main() {
	worker.ParseFlags()
	p := &Person{}
	worker.PayloadFromJSON(p)
	fmt.Println("Hello go! My name is ", p.Name)
	fmt.Println("Task id:", worker.TaskId)
	config, _ := worker.ConfigAsString()
	fmt.Println("Config:", config)
}
