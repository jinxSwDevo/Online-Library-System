import './App.css';
import { BrowserRouter , Route, Routes } from "react-router-dom";
import Home from './pages/home/Home';
import Login from './pages/login/Login';
import Register from './pages/register/Register';
import Notifications from './components/Notifications';
import Books from './pages/books/Books';
import Favorite from './pages/favorite/Favorite';
import WelcomePage from './pages/welcomepage/Welcomepage'
import Users from './pages/users/Users'
import Borrowed from './pages/borrowed/Borrowed'
import AddBook from './pages/addbook/AddBook'
import ViewBook from './pages/addbook/ViewBook'
import BookDetails from './pages/addbook/BookDetails'
import EditBook from './pages/addbook/EditBook';

function App() {
  return (
    <div className="App">
      <BrowserRouter>
        <Routes>
          <Route path="/" element={<WelcomePage />} />
          <Route path="/login" element={<Login />} />
          <Route path="/register" element={<Register />} />
          <Route path="/users" element={<Users />} />
          <Route path="/borrowed" element={<Borrowed/>} />
          <Route path="/books" element={<Books />} />
          <Route path="/notifications" element={<Notifications />} />
          <Route path="/favorite/:userId" element={<Favorite />} />
          <Route path="/viewbook/add" element={<AddBook />} />
          <Route path="/EditBook/:BookID" element={<EditBook />} />
          <Route path="/ViewBook" element={<ViewBook />} />
          <Route path="/bookdetails/:BookID" element={<BookDetails />} />
        </Routes>
      </BrowserRouter>
    </div>
  );
}

export default App;