import SwiftUI
import Combine
import shared

struct ContentView: View {
    @ObservedObject private var viewModel: ViewModel = ViewModel()
    @State private var isDisableDownloadButton = false

    var body: some View {
        NavigationView {
            VStack {
                List(viewModel.genresList, id: \.self) { item in
                    NavigationLink(item.name!) {
                        ScreenListMovies(genreItem: item)
                    }
                }
            }
                    .navigationBarTitle("Géneros")
                    .navigationBarItems(trailing:
                    Button(action: {
                        viewModel.getGenres()
                        isDisableDownloadButton = true
                    }) {
                        Image(systemName: "arrow.down")
                    }
                            .disabled(isDisableDownloadButton)
                    )
        }
    }
}

class ViewModel: ObservableObject {
    @Published var genresList: [Genre] = []

    func getGenres() {
        Repository().getGenres { result, error in
            DispatchQueue.main.async {
                self.genresList = result?.genres ?? []
            }
        }
    }
}
