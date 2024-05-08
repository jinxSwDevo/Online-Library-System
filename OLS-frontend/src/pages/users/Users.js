import React, { useState, useEffect } from "react";
import "./users.css";
import Navbar from "../../components/Navbar";
import Swal from "sweetalert2";

const Users = () => {
  const [users, setUsers] = useState([]);

  // Function to fetch users from the API on component mount
  useEffect(() => {
    fetchData();
  }, []);

  // Function to fetch users data from the API
  const fetchData = async () => {
    try {
      const response = await fetch("http://localhost:8080/api/users");
      console.log(response)
      if (!response.ok) {
        throw new Error("Failed to fetch users");
      }
      const data = await response.json();
      setUsers(data); // Update the local state with fetched users
    } catch (error) {
      console.error("Error fetching users:", error);
    }
  };

  // Function to handle accepting a user
  const handleAccept = async (userId) => {
    try {
      const response = await fetch(`http://localhost:8081/api/users/activate/${userId}`, {
        method: "PUT",
        headers: {
          "Content-Type": "application/json",
        },
        body: JSON.stringify({ acceptable: true }),
      });

      if (!response.ok) {
        throw new Error("Failed to accept user");
      }

      // Update the local state to reflect the accepted user
      const updatedUsers = users.map((user) => {
        if (user.id === userId) {
          return { ...user, acceptable: true };
        }
        return user;
      });
      setUsers(updatedUsers); // Update the state with the modified users array
    } catch (error) {
      console.error("Error accepting user:", error);
    }
  };

  // Function to handle deleting a user
  const deleteUser = (userId) => {
    Swal.fire({
      title: `Are you sure you want to delete this user?`,
      showCancelButton: true,
    }).then((result) => {
      if (result.isConfirmed) {
        fetch(`http://localhost:8081/api/users/${userId}`, {
          method: "DELETE",
        })
          .then((response) => {
            if (!response.ok) {
              throw new Error("Failed to delete user");
            }
            return response.json();
          })
          .then((data) => {
            console.log("User deleted successfully");
            // Filter out the deleted user from the local state
            const filteredUsers = users.filter((user) => user.id !== userId);
            setUsers(filteredUsers); // Update the state with the filtered users array
          })
          .catch((error) => {
            
            const filteredUsers = users.filter((user) => user.id !== userId);
            setUsers(filteredUsers);
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
              <h1>Users</h1>
              <table className="table">
                <thead>
                  <tr>
                    <th>ID</th>
                    <th>Name</th>
                    <th>Email</th>
                    <th>Password</th>
                    {/* Conditionally render the "Action" column header */}
                    {!users.every((user) => user.acceptable) && <th>Action</th>}
                    <th>Delete</th>
                  </tr>
                </thead>
                <tbody>
                  {users.map((user, index) => (
                    <tr key={index}>
                      <td>{user.id}</td>
                      <td>{user.name}</td>
                      <td>{user.email}</td>
                      <td>{user.password}</td>
                      {/* Conditionally render the "Action" column */}
                      {!user.acceptable && (
                        <td>
                          <button onClick={() => handleAccept(user.id)}>Accept</button>
                        </td>
                      )}
                      <td>
                        <button onClick={() => deleteUser(user.id)}>Delete User</button>
                      </td>
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

export default Users;
