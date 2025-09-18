<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Book Management</title>
    <!-- Bootstrap CSS -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
<div class="container mt-5">
    <h2 class="mb-4">Book Management</h2>

    <!-- Input Form -->
    <div class="card p-4 mb-4">
        <div class="row g-3">
            <div class="col-md-3">
                <input type="number" class="form-control" id="bookId" placeholder="Book ID">
            </div>
            <div class="col-md-3">
                <input type="text" class="form-control" id="bookName" placeholder="Book Name">
            </div>
            <div class="col-md-3">
                <input type="text" class="form-control" id="bookAuthor" placeholder="Author">
            </div>
            <div class="col-md-3">
                <input type="number" class="form-control" id="bookYear" placeholder="Year">
            </div>
        </div>

        <div class="mt-3">
            <button class="btn btn-success me-2" onclick="addBook()">Add</button>
            <button class="btn btn-warning me-2" onclick="updateBook()">Update</button>
            <button class="btn btn-danger me-2" onclick="deleteBook()">Delete</button>
            <button class="btn btn-primary" onclick="viewAllBooks()">View All</button>
        </div>
    </div>

    <!-- Table -->
    <div class="card p-4">
        <table class="table table-bordered table-striped" id="bookTable">
            <thead class="table-dark">
            <tr>
                <th>ID</th>
                <th>Name</th>
                <th>Author</th>
                <th>Year</th>
            </tr>
            </thead>
            <tbody>
            <!-- Book rows will appear here -->
            </tbody>
        </table>
    </div>
</div>

<!-- Bootstrap JS + Optional Popper -->
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>

<script>

    window.onload = function () {
        viewAllBooks();
    };

    // let books = [];

    function addBook() {
        fetch('/java_ee_war_exploded/book', {   // make sure servlet URL is correct
            method: 'POST',
            headers: {
                'Content-type': 'application/json; charset=UTF-8',
            },
            body: JSON.stringify({
                id: document.getElementById('bookId').value,
                name: document.getElementById('bookName').value,
                author: document.getElementById('bookAuthor').value,
                year: document.getElementById('bookYear').value,
            }),
        })
            .then(response => {
                if (!response.ok) {
                    return response.text()
                        .then(text => {
                        throw new Error("Server error (" + response.status + "): " + text);
                    });
                }
                return response.json(); // parse JSON
            })
            .then(data => {
                console.log("Server response:", data);

                if (data.status === "success") {
                    // books.push(data.book);   // add the new book to array
                    viewAllBooks();          // refresh table after adding
                    clearForm();             // clear input fields
                } else {
                    alert(data.message);
                }
            })
            .catch(error => console.error('Error book not add:', error));
    }

    function updateBook() {
        const id = document.getElementById('bookId').value;

        fetch('/java_ee_war_exploded/book', {//servalte eke thina webservalet url ekata pass wei
            method: 'PUT',
            headers: {
                'Content-type': 'application/json; charset=UTF-8',
            },
            body: JSON.stringify({//json string ekakata convert karai.
                id: id,
                name: document.getElementById('bookName').value,
                author: document.getElementById('bookAuthor').value,
                year: document.getElementById('bookYear').value,
            })
        })
            .then(response => {
                return response.json();
            })
            .then(data => {
                console.log("Update response:", data);

                if (data.status === "success") {
                    viewAllBooks();
                    clearForm();
                } else {
                    alert(data.message);
                }
            })
            .catch(error => console.error('Error book not update:', error));
    }


    function deleteBook() {
        const id = document.getElementById('bookId').value;

        fetch('/java_ee_war_exploded/book?id=' + id, {
            method: 'DELETE'
        })
            .then((response) => response.json())
            .then(data => {
                if (data.status === "success") {
                    viewAllBooks();
                    clearForm();
                } else {
                    console.error("Delete failed:", data.message);
                }
            })
            .catch(error => console.error('Error book not deleted:', error));
    }



    function viewAllBooks() {
        console.log('come')
        fetch('book', {
            method: 'GET',
            headers: {
                'Content-type': 'application/json; charset=UTF-8',
            },
        })
            .then((response) => response.json())
            .then((data) => {
                const tbody = document.querySelector('#bookTable tbody');//problem
                tbody.innerHTML = ''; // clear old rows

                data.forEach(b => {
                    const row = "<tr>" +
                        "<td>" + b.id + "</td>" +
                        "<td>" + b.name + "</td>" +
                        "<td>" + b.author + "</td>" +
                        "<td>" + b.year + "</td>" +
                        "</tr>";
                    tbody.innerHTML += row;
                });
            })
            .catch(error => console.error('Error fetching books:', error));
        console.log('come2')
    }

    function clearForm() {
        document.getElementById('bookId').value = '';
        document.getElementById('bookName').value = '';
        document.getElementById('bookAuthor').value = '';
        document.getElementById('bookYear').value = '';
    }
</script>
</body>
</html>
