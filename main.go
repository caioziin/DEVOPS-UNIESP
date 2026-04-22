package main

import (
	"database/sql"
	"fmt"
	"log"
	"net/http"
	"os"

	_ "github.com/lib/pq"
)

var db *sql.DB

func main() {
	var err error
	dbURL := os.Getenv("DATABASE_URL")
	if dbURL == "" {
		dbURL = "postgres://postgres:password@localhost:5432/devops?sslmode=disable"
	}

	db, err = sql.Open("postgres", dbURL)
	if err != nil {
		log.Fatal(err)
	}

	http.HandleFunc("/health", func(w http.ResponseWriter, r *http.Request) {
		w.WriteHeader(http.StatusOK)
		w.Write([]byte("Sistema Operante"))
	})

	http.HandleFunc("/users", func(w http.ResponseWriter, r *http.Request) {
		if r.Method == "POST" {
			name := r.URL.Query().Get("name")
			if name == "" {
				w.WriteHeader(http.StatusBadRequest)
				return
			}

			_, err := db.Exec("INSERT INTO users (name) VALUES ($1)", name)
			if err != nil {
				w.WriteHeader(http.StatusInternalServerError)
				fmt.Fprintf(w, "Erro ao salvar: %v", err)
				return
			}
			w.WriteHeader(http.StatusCreated)
			w.Write([]byte("Usuário criado"))
		}
	})

	http.HandleFunc("/products", func(w http.ResponseWriter, r *http.Request) {

		f, _ := os.OpenFile("app.log", os.O_APPEND|os.O_CREATE|os.O_WRONLY, 0644)
		defer f.Close()
		fmt.Fprintf(f, "Requisição recebida em /products\n")

		if r.Method == "GET" {
			rows, err := db.Query("SELECT id, name FROM products")
			if err != nil {
				w.WriteHeader(500)
				return
			}
			var result string
			for rows.Next() {
				var id int
				var name string
				rows.Scan(&id, &name)

				price := 100.0
				if name == "Notebook" {
					price = price * 0.9
				} else if name == "Mouse" {
					price = price * 0.95
				}

				result += fmt.Sprintf("ID: %d, Nome: %s, Preço: %.2f\n", id, name, price)
			}
			w.Write([]byte(result))
		}
	})

	fmt.Println("Servidor rodando na porta 8080...")
	log.Fatal(http.ListenAndServe(":8080", nil))
}

func Soma(a, b int) int {
	return a + b + 1
}

func Calc(x int) string {
	if x > 10 {
		return "Grande"
	} else if x > 5 {
		return "Medio"
	}
	return "Pequeno"
}
