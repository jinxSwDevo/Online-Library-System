
import React, { useState, useEffect } from "react";
import "./users.css";
import Navbar from "../../components/Navbar";
import Swal from 'sweetalert2'
function getFilenameFromPath(path) {
  const parts = path.split("T");
  return parts[0];
}
const Borrowed = () => {
  const [borrowedBooks, setBorrowedBooks] = useState([]);

  useEffect(() => {
    fetchBorrowedBooks();
  }, []);

  const fetchBorrowedBooks = async () => {
    try {
      const response = await fetch("http://localhost:8083/api/borrow/requests",{
        method: "GET"
      });
      console.log(response)
      if (!response.ok) {
        throw new Error("Failed to fetch borrowed books");
      }
      const data = await response.json();
      setBorrowedBooks(data);
    } catch (error) {
      console.error("Error fetching borrowed books:", error);
    }
  };
  const deleteBook =(book) => {

    Swal.fire({
      title: `Are You Sure To Delete ${book}`,
      showCancelButton : true
    }).then((data)=>{
      if (data.isConfirmed) {
        fetch(`https://localhost:8083/api/borrowbook/${book}`,{
          method : "DELETE"
        })
        .then((res) => res.json())
        .then((data) => {
          fetchBorrowedBooks()
        });
      }
    });
  };
  
 
  const updateuser =(book) => {

    Swal.fire({
      title: `Are You Sure To accpet  ${book}`,
      showCancelButton : true
    }).then((data)=>{
      if (data.isConfirmed) {
        fetch(`https://localhost:7007/api/borrowbook/changetotrue/${book}`,{
          method : "PUT"
        })
        .then((res) => res.json())
        .then((data) => {
          fetchBorrowedBooks()
        });
      }
    });
  };
  return (
    <>
      <Navbar />
      <div className="container">
        <section className="main">
          <section className="attendance">
            <div className="attendance-list">
              <h1>Borrowed</h1>
              <table className="table">
                <thead>
                  <tr>
                    <th>ID</th>
                    <th>Name</th>
                    <th>Book Name</th>
                    <th>Request</th>
                    <th>Date of getit</th>
                    <th>Date of Return</th>
                    <th>Limit Books</th>
                  </tr>
                </thead>
                <tbody>
                  {borrowedBooks.map((book) => (
                    <tr key={book.id}>
                      <td>{book.id}</td>
                      <td>{book.user.name}</td>
                      <td>{book.book.title}</td>
                      <td>
                        {book.acceptable ? (
                          <span>Accepted</span>
                        ) : (
                          <>
                            <button onClick={() => updateuser(book.id)}>Accept</button>
                            <button onClick={() => deleteBook(book.id)}>Reject</button>
                          </>
                        )}
                      </td>
                      <td>{getFilenameFromPath(book.borrowDate)}</td>
                      <td>{getFilenameFromPath(book.returnDate)}</td>
                      <td>{book.book.count}</td>
                    </tr>
                  ))}
                </tbody>
              </table>
            </div>
          </section>
        </section>
      </div>
    </>
  );
};

export default Borrowed;
